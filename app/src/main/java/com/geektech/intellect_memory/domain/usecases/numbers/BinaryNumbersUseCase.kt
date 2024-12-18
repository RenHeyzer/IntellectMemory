package com.geektech.intellect_memory.domain.usecases.numbers

import com.geektech.intellect_memory.domain.repositories.NumbersRepository
import javax.inject.Inject

class BinaryNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository
) {

    fun execute(quantitynumber: Int) = repository.generateBinaryNumbers(quantitynumber)
}