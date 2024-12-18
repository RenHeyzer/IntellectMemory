package com.geektech.intellect_memory.domain.repositories

import com.geektech.intellect_memory.common.resource.Resource
import com.geektech.intellect_memory.domain.models.PictureImageModel
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    fun fetchImagesDefault(): Flow<Resource<List<PictureImageModel>>>
}