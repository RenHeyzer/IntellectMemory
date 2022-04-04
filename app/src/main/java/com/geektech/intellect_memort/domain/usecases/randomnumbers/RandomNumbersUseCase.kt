package com.geektech.intellect_memort.domain.usecases.randomnumbers

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class RandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository,
) {
      operator fun invoke(quantitynumber: Int) = repository.generateRandomNumbers(quantitynumber)
}