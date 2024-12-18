package com.geektech.intellect_memory.domain.usecases.answernumbers

import com.geektech.intellect_memory.domain.repositories.AnswerRandomRepository
import javax.inject.Inject

class DeleteAllAnswerNumbersUseCase @Inject constructor(
    private val repository: AnswerRandomRepository,
) {

    suspend fun execute() = repository.deleteAllAnswerOfNumbers()
}