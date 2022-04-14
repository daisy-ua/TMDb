package com.tmdb.network.models

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("iso_3166_1")
    val code: String,

    val name: String
)
