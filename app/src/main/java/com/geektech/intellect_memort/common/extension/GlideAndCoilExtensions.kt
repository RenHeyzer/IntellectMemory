package com.geektech.intellect_memort.common.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadUrlWithCoil(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrlWithCoil.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .data(url)
        .size(110,170)
        .target(this)
        .build()
    imageLoader.enqueue(request)
}