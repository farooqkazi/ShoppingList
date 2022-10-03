package com.assignment.products.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.products.data.entity.Product
import com.assignment.products.domain.products.LoadAllProductsUseCase
import com.assignment.products.domain.wishproducts.AddWishlistProductUseCase
import com.assignment.products.domain.wishproducts.RemoveWishlistProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val loadAllProductsUseCase: LoadAllProductsUseCase,
    private val addWishlistProductUseCase: AddWishlistProductUseCase,
    val removeWishlistProductUseCase: RemoveWishlistProductUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    private var productList = listOf<Product>()

    init {
        loadData()
    }

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        if (_uiState.value !is UiState.Loading) {
            _uiState.value = UiState.Loading
        }
        job = viewModelScope.launch {
            loadAllProductsUseCase(Unit).onSuccess { items ->
                productList = items.distinctBy { it.id }.sortedBy { it.name }
                _uiState.value = UiState.Success(productList)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Unknown Error")
            }
        }

    }

    fun onUpdateWishlistProduct(product: Product) {
        product.isWishlist = !product.isWishlist
        viewModelScope.launch {
            if (product.isWishlist) addWishlistProductUseCase(product)
            else removeWishlistProductUseCase(product)
            loadData()
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val msg: String) : UiState()
        class Success(val products: List<Product>) : UiState()
    }
}