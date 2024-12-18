package com.geektech.intellect_memory.domain.usecases.numbers

import com.geektech.intellect_memory.domain.repositories.NumbersRepository
import javax.inject.Inject

class DeleteAllNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository,
) {

    suspend fun execute() = repository.deleteAllRandomNumbers()
}