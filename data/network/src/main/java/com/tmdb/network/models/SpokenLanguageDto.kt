package com.tmdb.network.models

import com.google.gson.annotations.SerializedName

data class SpokenLanguageDto(
    @SerializedName("iso_639_1")
    val code: String,

    val name: String
)
