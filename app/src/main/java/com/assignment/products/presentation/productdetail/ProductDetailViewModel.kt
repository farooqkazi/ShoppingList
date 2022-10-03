package com.assignment.products.presentation.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.products.data.entity.Product
import com.assignment.products.domain.cartproducts.AddCartProductUseCase
import com.assignment.products.domain.cartproducts.LoadCartProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val cartProductsUseCase: LoadCartProductsUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
) : ViewModel() {
    private val product = ProductDetailFragmentArgs.fromSavedStateHandle(stateHandle).product
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        loadCartProducts()
    }

    private fun loadCartProducts() {
        if (_uiState.value !is UiState.Loading) {
            _uiState.value = UiState.Loading
        }
        viewModelScope.launch {
            cartProductsUseCase(Unit).onSuccess { items ->
                _uiState.value = UiState.Success(
                    product, items.any { it.id == product.id }
                )
            }.onFailure {
                //display product details in error state with enabling the action button
                _uiState.value = UiState.Success(product, false)
            }
        }
    }

    fun addProductToCart() {
        viewModelScope.launch {
            addCartProductUseCase(product)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Success(val product: Product, val isAddedToCart: Boolean) : UiState()
    }
}
