package com.geektech.intellect_memort.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}


fun ImageView.loadImageWithFixSize(url: String) {
    Glide.with(this)
        .load(url)
        .override(50, 80)
        .into(this)
}
