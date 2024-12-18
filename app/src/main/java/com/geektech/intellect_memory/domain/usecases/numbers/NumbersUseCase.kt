package com.geektech.intellect_memory.domain.usecases.numbers

import com.geektech.intellect_memory.domain.repositories.NumbersRepository
import javax.inject.Inject

class NumbersUseCase @Inject constructor(
    private val repository: NumbersRepository,
) {
      operator fun invoke(quantitynumber: Int) = repository.generateRandomNumbers(quantitynumber)
}