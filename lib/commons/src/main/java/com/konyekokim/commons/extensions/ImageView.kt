package com.konyekokim.commons.extensions

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

private val imageRequestOptions by lazy {
    RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
}

fun ImageView.loadImage(imageUrl: String?){
    if(!imageUrl.isNullOrEmpty() && imageUrl.endsWith("svg", true)){
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadImage.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .data(imageUrl)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    } else {
        Glide.with(context)
            .load(imageUrl)
            .apply(imageRequestOptions)
            .into(this)
    }
}