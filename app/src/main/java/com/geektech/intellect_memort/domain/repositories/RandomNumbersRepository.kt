package com.geektech.intellect_memort.domain.repositories

interface RandomNumbersRepository {
    suspend fun addRandomNumbers(item: HashMap<String, Any>) : Boolean

}