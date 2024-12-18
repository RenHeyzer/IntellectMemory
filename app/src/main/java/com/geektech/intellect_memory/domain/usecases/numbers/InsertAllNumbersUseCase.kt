package com.geektech.intellect_memory.domain.usecases.numbers

import com.geektech.intellect_memory.domain.models.NumbersModel
import com.geektech.intellect_memory.domain.repositories.NumbersRepository
import javax.inject.Inject

class InsertAllNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository,
) {

    suspend fun execute(numbers: List<NumbersModel>) =
        repository.insertAllRandomNumbers(numbers)
}