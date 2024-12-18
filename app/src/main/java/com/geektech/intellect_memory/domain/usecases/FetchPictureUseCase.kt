package com.geektech.intellect_memory.domain.usecases

import com.geektech.intellect_memory.domain.repositories.PictureRepository
import javax.inject.Inject

class FetchPictureUseCase @Inject constructor(
    private val repository: PictureRepository,
) {

    fun execute() = repository.fetchImagesDefault()
}