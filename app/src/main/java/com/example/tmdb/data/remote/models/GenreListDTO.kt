package com.example.tmdb.data.remote.models

import com.example.tmdb.data.models.Genre
import com.google.gson.annotations.SerializedName

data class GenreListDTO(
    @SerializedName("genres")
    val items: List<Genre>)