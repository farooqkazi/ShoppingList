package com.assignment.products.domain.products

import com.assignment.products.data.entity.Product
import com.assignment.products.di.DefaultDispatcher
import com.assignment.products.domain.SuspendUseCase
import com.assignment.products.domain.wishproducts.WishlistRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadAllProductsUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val productsRepository: ProductsRepository,
    private val wishlistRepository: WishlistRepository,
) : SuspendUseCase<Unit, List<Product>>(dispatcher) {

    override suspend fun execute(i: Unit): List<Product> {
        val wishlistProducts = wishlistRepository.getWishlistProducts()

        return productsRepository.getAllProducts().onEach { product ->
            //to mark the wishlist products
            product.isWishlist = wishlistProducts.any { it.id == product.id }
        }
    }
}