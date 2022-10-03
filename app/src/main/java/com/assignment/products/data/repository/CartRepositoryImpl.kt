package com.assignment.products.data.repository

import com.assignment.products.data.entity.Product
import com.assignment.products.data.local.cartProducts
import com.assignment.products.domain.cartproducts.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(): CartRepository {
    override suspend fun getCartProducts(): List<Product> {
        return cartProducts
    }

    override suspend fun addProductToCart(product: Product) {
        cartProducts.add(product)
    }
}