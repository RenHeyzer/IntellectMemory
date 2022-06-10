package com.geektech.intellect_memort.domain.models

import com.geektech.intellect_memort.common.base.SBaseDiffModel
import java.io.Serializable

data class PictureImageModel(
    override val id: String? = null,
    val imageUrl: String? = null,
) : SBaseDiffModel, Serializable


data class PictureImagesList(
    val images: List<PictureImageModel>,
) : Serializable