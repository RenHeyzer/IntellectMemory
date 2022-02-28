package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import javax.inject.Inject

class DeleteRandomNumbersUseCase @Inject constructor(
    private val repository: RandomNumbersRepository
) {

    suspend operator fun invoke() = repository.deleteDocuments()
}