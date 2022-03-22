package com.geektech.intellect_memort.domain.usecases.randomnumbers

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class UploadRandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository
) {

    suspend operator fun invoke(quantity:Int) = repository.uploadRandomNumbers(quantity)
}