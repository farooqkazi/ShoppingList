package com.assignment.products.presentation.productlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.assignment.products.R
import com.assignment.products.data.entity.Product
import com.assignment.products.databinding.FragmentProductListBinding
import com.assignment.products.presentation.common.EmptyStatePresenter
import com.assignment.products.presentation.common.snackRetry
import com.assignment.products.presentation.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list), (Product) -> Unit,
    EmptyStatePresenter {

    override fun getEmptyStateLayout() = binding.emptyLayout
    private val binding by viewBinding<FragmentProductListBinding>()
    private val viewModel by viewModels<ProductListViewModel>()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProductAdapter(this) {
            viewModel.onUpdateWishlistProduct(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadData()
            }
            toolbarWishlistIcon.setOnClickListener {
                val direction = ProductListFragmentDirections.actionProductListToWishlist()
                findNavController().navigate(direction)
            }
        }
        observeUIState()
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
    }

    private fun updateUI(uiState: ProductListViewModel.UiState) {
        when (uiState) {
            is ProductListViewModel.UiState.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                if (adapter.itemCount > 0) {
                    snackRetry(
                        "No Internet connection! Please try again.",
                        callback = viewModel::loadData
                    )
                } else {
                    showNetworkError(viewModel::loadData)
                }
            }
            is ProductListViewModel.UiState.Loading ->
                binding.swipeRefreshLayout.isRefreshing = true
            is ProductListViewModel.UiState.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                if (uiState.products.isNotEmpty()) {
                    showContent()
                } else {
                    showEmptyState("No Cakes data available right now!")
                }
                adapter.submitList(uiState.products)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun invoke(product: Product) {
        val direction = ProductListFragmentDirections.actionProductListToDetail(product)
        findNavController().navigate(direction)
    }

}