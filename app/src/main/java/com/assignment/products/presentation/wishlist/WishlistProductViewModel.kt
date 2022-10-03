package com.assignment.products.presentation.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.products.data.entity.Product
import com.assignment.products.domain.wishproducts.LoadWishlistProductsUseCase
import com.assignment.products.domain.wishproducts.RemoveWishlistProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistProductViewModel @Inject constructor(
    private val removeWishlistProductUseCase: RemoveWishlistProductUseCase,
    private val loadWishlistProductsUseCase: LoadWishlistProductsUseCase,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>>
        get() = _products

    init {
        loadWishlistProducts()
    }

    private fun loadWishlistProducts() {
        viewModelScope.launch {
            loadWishlistProductsUseCase(Unit).onSuccess {
                _products.value = it
            }.onFailure {
                //TODO this case will ba handled later when Room DB is used
            }
        }
    }

    fun removeProductFromWishlist(product: Product) {
        viewModelScope.launch {
            removeWishlistProductUseCase(product)
        }
    }
}