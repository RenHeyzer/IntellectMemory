package com.geektech.intellect_memory.domain.repositories

import com.geektech.intellect_memory.domain.models.AnswerNumbersModel
import kotlinx.coroutines.flow.Flow

interface AnswerRandomRepository {

    fun getAllAnswerOfNumbers(): Flow<List<AnswerNumbersModel>>

    suspend fun insertAllAnswerOfNumbers(numbers: List<AnswerNumbersModel>)

    suspend fun deleteAllAnswerOfNumbers()
}