package com.geektech.intellect_memort.domain.usecases.answernumbers

import com.geektech.intellect_memort.domain.repositories.AnswerRandomRepository
import javax.inject.Inject

class DeleteAllAnswerNumbersUseCase @Inject constructor(
    private val repository: AnswerRandomRepository,
) {

    suspend fun execute() = repository.deleteAllAnswerOfNumbers()
}