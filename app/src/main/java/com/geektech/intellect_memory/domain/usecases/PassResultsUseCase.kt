package com.geektech.intellect_memory.domain.usecases

import com.geektech.intellect_memory.domain.repositories.ResultsRepository
import javax.inject.Inject

class PassResultsUseCase @Inject constructor(
    private val repository: ResultsRepository
) {
    suspend fun execute(points: Int) = repository.passResults(points)
}