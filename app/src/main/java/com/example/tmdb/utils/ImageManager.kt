package com.example.tmdb.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.tmdb.data.network.R
import jp.wasabeef.glide.transformations.BlurTransformation

object ImageManager {
    private const val BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun getImage(view: AppCompatImageView, url: String?) =
        Glide.with(view.context)
            .load(BASE_URL + url)
            .error(R.drawable.ic_image_not_found)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .optionalCenterCrop()
            .into(view)

    fun getBlurredImage(view: AppCompatImageView, url: String?) {
        Glide.with(view.context)
            .load(BASE_URL + url)
            .apply(bitmapTransform(BlurTransformation(8, 8)))
            .into(view)
    }
}
