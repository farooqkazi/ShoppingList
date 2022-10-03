package com.assignment.products.data.entity

data class ProductResponse(
    val title: String,
    val currency: String,
    val items: List<Product>,
)