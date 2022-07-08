package com.example.tmdb.ui.utils.rvdecorators

import android.content.res.Resources
import kotlin.math.roundToInt

internal val Float.toPx get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
