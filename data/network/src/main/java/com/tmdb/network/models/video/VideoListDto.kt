package com.tmdb.network.models.video

import com.google.gson.annotations.SerializedName

data class VideoDto(

    val name: String?,

    val key: String?,

    val site: String?,

    val type: String?,

    val official: Boolean?,

    val id: String?,
)

data class VideoListDto(
    val id: Int?,

    @SerializedName("results")
    val videos: List<VideoDto>,
)