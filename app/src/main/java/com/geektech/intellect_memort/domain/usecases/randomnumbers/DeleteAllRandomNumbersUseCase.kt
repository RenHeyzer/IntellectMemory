package com.geektech.intellect_memort.domain.usecases.randomnumbers

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class DeleteAllRandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository,
) {

    suspend fun execute() = repository.deleteAllRandomNumbers()
}