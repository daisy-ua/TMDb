package com.example.tmdb.utils.converters

fun getDuration(duration: Int): String {
    val hours = duration / 60
    val minutes = duration % 60

    return if (hours == 0) "${minutes}m" else "${hours}h ${minutes}m"
}
