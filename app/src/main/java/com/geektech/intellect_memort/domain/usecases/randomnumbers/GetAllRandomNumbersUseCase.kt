package com.geektech.intellect_memort.domain.usecases.randomnumbers

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class GetAllRandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository,
) {

    fun execute() = repository.getAllRandomNumbers()
}