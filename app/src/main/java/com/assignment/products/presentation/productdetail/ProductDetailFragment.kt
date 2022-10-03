package com.assignment.products.presentation.productdetail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.assignment.products.R
import com.assignment.products.data.entity.Product
import com.assignment.products.databinding.FragmentProductDetailBinding
import com.assignment.products.presentation.common.viewBinding
import com.assignment.products.presentation.productdetail.ProductDetailViewModel.UiState.Loading
import com.assignment.products.presentation.productdetail.ProductDetailViewModel.UiState.Success
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {
    private val binding by viewBinding<FragmentProductDetailBinding>()
    private val viewModel by viewModels<ProductDetailViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeUIState()
    }

    private fun initViews() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            toolbarWishlistIcon.setOnClickListener {
                val direction = ProductDetailFragmentDirections.actionProductDetailToWishlist()
                findNavController().navigate(direction)
            }
            addToCartButton.setOnClickListener {
                binding.addToCartButton.isEnabled = false
                viewModel.addProductToCart()
            }
        }
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.uiState.flowWithLifecycle(lifecycle).collect {
            when (it) {
                is Loading ->
                    binding.swipeRefreshLayout.isRefreshing = true
                is Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.swipeRefreshLayout.isEnabled = false
                    updateUi(it.product, it.isAddedToCart)
                }
            }
        }
    }

    private fun updateUi(product: Product, isAddedToCart: Boolean) {
        with(binding) {
            //texts
            textName.text = product.name
            textBrand.text = product.brand
            badgesView.setBadges(product.badges)

            //prices
            textPrice.text = getString(
                R.string.price_with_currency, product.price, product.currency
            )
            textOriginalPrice.isVisible = product.originalPrice > 0
            textOriginalPrice.text = getString(
                R.string.price_with_currency, product.originalPrice, product.currency
            )
            textOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            //button
            addToCartButton.isEnabled = isAddedToCart.not()

            //image
            Glide.with(this@ProductDetailFragment)
                .load(product.image)
                .error(R.drawable.image_placeholder)
                .into(binding.image)
        }
    }
}