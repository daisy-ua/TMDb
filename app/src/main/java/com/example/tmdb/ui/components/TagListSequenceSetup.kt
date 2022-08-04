package com.example.tmdb.ui.components

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tmdb.R
import com.google.android.flexbox.FlexboxLayout

fun buildTagTextView(context: Context, name: String) = TextView(context).apply {
    text = name
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
    setTextColor(ContextCompat.getColor(context, R.color.gull_gray))
    background = ContextCompat.getDrawable(context, R.drawable.background_search_view)
    layoutParams = getParams()
    setPadding(32, 12, 32, 12)
}

private fun getParams(): FlexboxLayout.LayoutParams = FlexboxLayout.LayoutParams(
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
).apply { setMargins(0, 20, 16, 12) }
