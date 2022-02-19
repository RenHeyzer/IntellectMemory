package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class RandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository
) {
    suspend fun invoke(item:HashMap<String, Any>) = repository.addRandomNumbers(item)
}