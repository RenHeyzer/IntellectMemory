package com.geektech.intellect_memort.domain.usecases.numbers

import com.geektech.intellect_memort.domain.repositories.NumbersRepository
import javax.inject.Inject

class GetAllNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository,
) {

    fun execute() = repository.getAllRandomNumbers()
}