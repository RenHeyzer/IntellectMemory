package com.geektech.intellect_memory.domain.repositories

import com.geektech.intellect_memory.common.resource.Resource
import com.geektech.intellect_memory.domain.models.NumbersModel
import kotlinx.coroutines.flow.Flow

interface NumbersRepository {

    fun generateRandomNumbers(quantitynumber: Int): Flow<Resource<List<NumbersModel>>>

    fun generateBinaryNumbers(quantitynumber: Int): Flow<Resource<List<NumbersModel>>>

    suspend fun uploadRandomNumbers(quantity: Int)

    fun getAllRandomNumbers(): Flow<List<NumbersModel>>

    suspend fun insertAllRandomNumbers(numbers: List<NumbersModel>)

    suspend fun deleteAllRandomNumbers()
}
