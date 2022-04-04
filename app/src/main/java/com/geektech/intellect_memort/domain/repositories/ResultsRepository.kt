package com.geektech.intellect_memort.domain.repositories

interface ResultsRepository {

    suspend fun passResults(points: Int)
}