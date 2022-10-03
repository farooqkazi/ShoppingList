package com.assignment.products.presentation.wishlist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.products.R
import com.assignment.products.data.entity.Product
import com.assignment.products.databinding.WishlistItemLayoutBinding
import com.assignment.products.presentation.common.ProductDiffCallback
import com.bumptech.glide.Glide


class WishlistAdapter(
    private val onRemoveClick: (Product) -> Unit,
) : ListAdapter<Product, WishlistAdapter.VH>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WishlistItemLayoutBinding.inflate(layoutInflater, parent, false)
        binding.textOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.remove.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class VH(private val wishlistItemBinding: WishlistItemLayoutBinding) :
        RecyclerView.ViewHolder(wishlistItemBinding.root) {

        fun bind(product: Product) {
            with(wishlistItemBinding) {
                val context = root.context
                //texts
                textName.text = product.name
                textBrand.text = product.brand

                //prices
                textPrice.text = context.getString(
                    R.string.price_with_currency, product.price, product.currency
                )
                textOriginalPrice.isVisible = product.originalPrice > 0
                textOriginalPrice.text = context.getString(
                    R.string.price_with_currency, product.originalPrice, product.currency
                )

                //remove button
                remove.setOnClickListener {
                    onRemoveClick.invoke(product)
                    notifyItemRemoved(adapterPosition)
                }

                //image
                Glide.with(image).load(product.image)
                    .error(R.drawable.image_placeholder).into(image)
            }
        }
    }
}


