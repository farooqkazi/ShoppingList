package com.assignment.products.domain.products

import com.assignment.products.data.entity.Product
import com.assignment.products.data.entity.ProductResponse

interface ProductsRepository {
    suspend fun getAllProducts(): List<Product>
}