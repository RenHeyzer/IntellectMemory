package com.geektech.intellect_memort.domain.repositories

import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import kotlinx.coroutines.flow.Flow

interface AnswerRandomNumbersRepository {

    fun getAllAnswerOfNumbers(): Flow<List<AnswerNumbersModel>>

    suspend fun insertAnswerOfNumber(number: AnswerNumbersModel)

    suspend fun deleteAllAnswerOfNumbers()

    suspend fun deleteAnswerOfNumber(number: AnswerNumbersModel)
}