package com.example.tmdb.utils.components

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tmdb.R

fun getTagTextView(context: Context, name: String) = TextView(context).apply {
    text = name
    background = ContextCompat.getDrawable(context, R.drawable.container_rate)
    layoutParams = getParams()
    setPadding(8, 5, 8, 5)
}

private fun getParams() : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
).apply { setMargins(16, 16, 16, 16) }