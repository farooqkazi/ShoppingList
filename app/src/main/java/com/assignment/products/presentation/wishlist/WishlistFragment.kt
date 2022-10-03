package com.assignment.products.presentation.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.products.R
import com.assignment.products.data.entity.Product
import com.assignment.products.databinding.FragmentWishlistBinding
import com.assignment.products.presentation.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment(R.layout.fragment_wishlist), (Product) -> Unit {

    private val binding by viewBinding<FragmentWishlistBinding>()
    private val viewModel by viewModels<WishlistProductViewModel>()
    private lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = WishlistAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(), LinearLayoutManager.VERTICAL
            )
            recyclerView.addItemDecoration(dividerItemDecoration)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            toolbarCloseIcon.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        observeUIState()
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.products.flowWithLifecycle(lifecycle).collect {
            adapter.submitList(it)
            updateToolbarTitle(it.size)
        }
    }

    private fun updateToolbarTitle(count: Int) {
        binding.toolbar.title = getString(R.string.wishlist_title, count)

    }

    override fun invoke(p1: Product) {
        viewModel.removeProductFromWishlist(p1)
        updateToolbarTitle(adapter.itemCount - 1)
    }
}

