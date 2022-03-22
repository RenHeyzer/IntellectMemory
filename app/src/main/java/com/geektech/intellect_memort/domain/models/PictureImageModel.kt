package com.geektech.intellect_memort.domain.models

import java.io.Serializable

data class PictureImageModel(
    val id: String? = null,
    val imageUrl: String? = null,
)

data class PictureImagesList(
    val images: List<PictureImageModel>,
) : Serializable

