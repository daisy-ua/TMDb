package com.example.tmdb.ui.components

import android.util.TypedValue
import android.view.ViewGroup
import com.example.tmdb.R
import com.google.android.material.chip.Chip

fun buildTagChip(view: ViewGroup, name: String, id: Int? = null) = Chip(
    view.context,
    null,
    R.attr.ChipChoiceStyle
).apply {
    text = name
    id?.let { this.id = it }
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
    view.addView(this)
}

