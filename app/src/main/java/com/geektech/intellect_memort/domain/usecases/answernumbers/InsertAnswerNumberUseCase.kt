package com.geektech.intellect_memort.domain.usecases.answernumbers

import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.repositories.AnswerRandomRepository
import javax.inject.Inject

class InsertAnswerNumberUseCase @Inject constructor(
    private val repository: AnswerRandomRepository,
) {

    suspend fun execute(numbers: List<AnswerNumbersModel>) =
        repository.insertAllAnswerOfNumbers(numbers)
}