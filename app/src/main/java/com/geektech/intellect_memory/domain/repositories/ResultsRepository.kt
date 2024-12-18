package com.geektech.intellect_memory.domain.repositories

interface ResultsRepository {

    suspend fun passResults(points: Int)
}