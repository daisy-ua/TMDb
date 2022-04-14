package com.example.tmdb.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.tmdb.data.network.R
import jp.wasabeef.glide.transformations.BlurTransformation

object ImageManager {
    private const val BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun getImage(view: AppCompatImageView, url: String) =
        Glide.with(view.context)
            .load(BASE_URL + url)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image_not_supported)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)

    fun getBlurredImage(view: AppCompatImageView, url: String) {
        Glide.with(view.context)
            .load(BASE_URL + url)
            .apply(bitmapTransform(BlurTransformation(5, 3)))
            .into(view)
    }
}
