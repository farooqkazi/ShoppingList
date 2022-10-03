package com.assignment.products.presentation.common

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.assignment.products.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.ShapeAppearanceModel

class BadgesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    init {
        isClickable = false
        isFocusable = false
        isSelectionRequired = false
    }

    fun setBadges(list: List<String>) {
        removeAllViews()
        list.forEach { addView(createChipView(it)) }
    }

    private fun createChipView(text: String) = Chip(context).apply {
        setText(text)
        isCheckable = false
        isClickable = false
        isFocusable = false
        setTextColor(ContextCompat.getColor(context, R.color.text_title))
        setChipBackgroundColorResource(R.color.grey_light)
        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8.px.toFloat())
    }
}