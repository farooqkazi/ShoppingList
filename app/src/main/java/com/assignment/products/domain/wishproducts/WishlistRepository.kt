package com.assignment.products.domain.wishproducts

import com.assignment.products.data.entity.Product

interface WishlistRepository {
    suspend fun getWishlistProducts(): List<Product>
    suspend fun addWishlistProduct(product: Product)
    suspend fun removeWishlistProduct(product: Product)
}