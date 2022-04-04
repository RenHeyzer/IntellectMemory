package com.geektech.intellect_memort.domain.repositories

import com.geektech.intellect_memort.common.resource.Resource
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import kotlinx.coroutines.flow.Flow

interface RandomNumbersRepository {

     fun generateRandomNumbers(quantitynumber: Int): Flow<Resource<List<RandomNumbersModel>>>

     suspend fun uploadRandomNumbers(quantity:Int)

     fun getAllRandomNumbers(): Flow<List<RandomNumbersModel>>

     suspend fun insertAllRandomNumbers(numbers: List<RandomNumbersModel>)

     suspend fun deleteAllRandomNumbers()
}
