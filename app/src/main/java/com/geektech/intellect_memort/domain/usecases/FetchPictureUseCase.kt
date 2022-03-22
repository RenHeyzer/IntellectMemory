package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.PictureRepository
import javax.inject.Inject

class FetchPictureUseCase @Inject constructor(
    private val repository: PictureRepository,
) {

    fun execute() = repository.fetchImagesDefault()
}