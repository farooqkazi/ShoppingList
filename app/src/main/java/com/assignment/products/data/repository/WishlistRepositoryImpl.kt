package com.assignment.products.data.repository

import com.assignment.products.data.entity.Product
import com.assignment.products.data.local.wishlistProducts
import com.assignment.products.domain.wishproducts.WishlistRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor() : WishlistRepository {
    override suspend fun getWishlistProducts(): List<Product> {
        return wishlistProducts
    }

    override suspend fun addWishlistProduct(product: Product) {
        wishlistProducts.add(product)
    }

    override suspend fun removeWishlistProduct(product: Product) {
        wishlistProducts.filter { it.id == product.id }.onEach {
            wishlistProducts.remove(it)
        }
    }
}