package com.geektech.intellect_memort.domain.repositories

import com.geektech.intellect_memort.common.resource.Resource
import com.geektech.intellect_memort.domain.models.PictureImageModel
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    fun fetchImagesDefault(): Flow<Resource<List<PictureImageModel>>>
}