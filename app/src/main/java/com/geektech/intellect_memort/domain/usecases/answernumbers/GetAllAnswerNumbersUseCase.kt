package com.geektech.intellect_memort.domain.usecases.answernumbers

import com.geektech.intellect_memort.domain.repositories.AnswerRandomNumbersRepository
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class GetAllAnswerNumbersUseCase @Inject constructor(
    private val repository: AnswerRandomNumbersRepository,
) {

    fun execute() = repository.getAllAnswerOfNumbers()
}