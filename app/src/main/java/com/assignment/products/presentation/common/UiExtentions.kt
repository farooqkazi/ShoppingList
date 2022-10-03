package com.assignment.products.presentation.common

import android.content.res.Resources.getSystem
import android.view.View

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

