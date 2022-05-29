package com.geektech.intellect_memort.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(image: String?) {
    Glide.with(this.context)
        .load(image)
        .into(this)
}