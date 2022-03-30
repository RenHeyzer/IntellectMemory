package com.geektech.intellect_memort.domain.usecases.answernumbers

import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.repositories.AnswerRandomNumbersRepository
import javax.inject.Inject

class InsertAnswerNumberUseCase @Inject constructor(
    private val repository: AnswerRandomNumbersRepository,
) {

    suspend fun execute(numbers: List<AnswerNumbersModel>) =
        repository.insertAllAnswerOfNumbers(numbers)
}