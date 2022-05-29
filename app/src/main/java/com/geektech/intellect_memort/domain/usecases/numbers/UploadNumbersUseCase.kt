package com.geektech.intellect_memort.domain.usecases.numbers

import com.geektech.intellect_memort.domain.repositories.NumbersRepository
import javax.inject.Inject

class UploadNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository
) {

    suspend operator fun invoke(quantity:Int) = repository.uploadRandomNumbers(quantity)
}