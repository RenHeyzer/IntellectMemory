package com.geektech.intellect_memort.domain.usecases.numbers

import com.geektech.intellect_memort.domain.models.NumbersModel
import com.geektech.intellect_memort.domain.repositories.NumbersRepository
import javax.inject.Inject

class InsertAllNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository,
) {

    suspend fun execute(numbers: List<NumbersModel>) =
        repository.insertAllRandomNumbers(numbers)
}