package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class RandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository,
) {
      operator fun invoke(quantity: Int) = repository.generateRandomNumbers(quantity)
}