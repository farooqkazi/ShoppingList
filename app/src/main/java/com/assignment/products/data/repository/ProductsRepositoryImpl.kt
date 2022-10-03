package com.assignment.products.data.repository

import com.assignment.products.data.api.ProductsApi
import com.assignment.products.data.entity.Product
import com.assignment.products.domain.products.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsAPi: ProductsApi
) : ProductsRepository {
    override suspend fun getAllProducts(): List<Product> {
        val response = productsAPi.getAllProducts()
        return response.items.onEach { it.currency = response.currency }
    }
}