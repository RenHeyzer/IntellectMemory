package com.geektech.intellect_memort.domain.usecases.answernumbers

import com.geektech.intellect_memort.domain.repositories.AnswerRandomRepository
import javax.inject.Inject

class GetAllAnswerNumbersUseCase @Inject constructor(
    private val repository: AnswerRandomRepository,
) {

    fun execute() = repository.getAllAnswerOfNumbers()
}