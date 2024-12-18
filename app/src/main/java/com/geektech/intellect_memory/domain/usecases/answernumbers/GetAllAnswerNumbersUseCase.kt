package com.geektech.intellect_memory.domain.usecases.answernumbers

import com.geektech.intellect_memory.domain.repositories.AnswerRandomRepository
import javax.inject.Inject

class GetAllAnswerNumbersUseCase @Inject constructor(
    private val repository: AnswerRandomRepository,
) {

    fun execute() = repository.getAllAnswerOfNumbers()
}