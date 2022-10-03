package com.assignment.products.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val sku: String,
    val image: String,
    val brand: String,
    val name: String,
    val price: Int,
    val originalPrice: Int,
    val badges: List<String>,
    var currency: String = "",
    var isWishlist: Boolean = false,
) : Parcelable