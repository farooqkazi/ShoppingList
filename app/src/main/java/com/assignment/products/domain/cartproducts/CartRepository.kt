package com.assignment.products.domain.cartproducts

import com.assignment.products.data.entity.Product

interface CartRepository {
    suspend fun getCartProducts(): List<Product>
    suspend fun addProductToCart(product: Product)
}