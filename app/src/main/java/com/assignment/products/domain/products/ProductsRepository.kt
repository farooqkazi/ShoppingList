package com.assignment.products.domain.products

import com.assignment.products.data.entity.Product

interface ProductsRepository {
    suspend fun getAllProducts(): List<Product>
}