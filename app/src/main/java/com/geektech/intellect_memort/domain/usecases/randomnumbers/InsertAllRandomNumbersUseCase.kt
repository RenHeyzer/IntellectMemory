package com.geektech.intellect_memort.domain.usecases.randomnumbers

import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class InsertAllRandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository,
) {

    suspend fun execute(numbers: List<RandomNumbersModel>) =
        repository.insertAllRandomNumbers(numbers)
}