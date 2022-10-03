package com.assignment.products.presentation.common

import androidx.recyclerview.widget.DiffUtil
import com.assignment.products.data.entity.Product

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}