package com.assignment.products.presentation.productlist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.products.R
import com.assignment.products.data.entity.Product
import com.assignment.products.databinding.ProductItemLayoutBinding
import com.assignment.products.presentation.common.ProductDiffCallback
import com.bumptech.glide.Glide


class ProductAdapter(
    private val onClickCallback: (Product) -> Unit,
    private val onFavouriteClick: (Product) -> Unit,
) : ListAdapter<Product, ProductAdapter.VH>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductItemLayoutBinding.inflate(layoutInflater, parent, false)
        binding.textOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClickCallback.invoke(product)
        }
    }

    inner class VH(private val productItemBinding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(productItemBinding.root) {

        fun bind(product: Product) {
            with(productItemBinding) {
                val context = root.context
                //texts
                textName.text = product.name
                textBrand.text = product.brand
                badgesView.setBadges(product.badges)

                //prices
                textPrice.text = context.getString(
                    R.string.price_with_currency, product.price, product.currency
                )
                textOriginalPrice.isVisible = product.originalPrice > 0
                textOriginalPrice.text = context.getString(
                    R.string.price_with_currency, product.originalPrice, product.currency
                )

                //favourite icon
                wishlistIcon.setImageResource(
                    if (product.isWishlist) R.drawable.ic_bookmark
                    else R.drawable.ic_bookmark_border
                )
                wishlistIcon.setOnClickListener {
                    onFavouriteClick.invoke(product)
                    notifyItemChanged(adapterPosition)
                }

                //image
                Glide.with(image).load(product.image)
                    .error(R.drawable.image_placeholder).into(image)
            }
        }
    }
}


