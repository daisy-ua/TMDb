package com.example.tmdb.utils.components

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tmdb.R
import com.google.android.flexbox.FlexboxLayout

fun buildTagTextView(context: Context, name: String) = TextView(context).apply {
    text = name
    background = ContextCompat.getDrawable(context, R.drawable.container_rate)
    layoutParams = getParams()
    setPadding(8, 5, 8, 5)
}

private fun getParams() : FlexboxLayout.LayoutParams = FlexboxLayout.LayoutParams(
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
).apply { setMargins(8, 10, 8, 10) }