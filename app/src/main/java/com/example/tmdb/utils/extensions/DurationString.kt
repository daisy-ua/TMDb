package com.example.tmdb.utils.extensions

fun getDuration(duration: Int): String {
    val hours = duration / 60
    val minutes = duration % 60

    return if (hours == 0) "${minutes}min" else "${hours}h ${minutes}min"
}
