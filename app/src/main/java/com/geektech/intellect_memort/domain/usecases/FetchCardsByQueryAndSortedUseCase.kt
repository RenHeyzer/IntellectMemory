package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.CardsRepository
import javax.inject.Inject

class FetchCardsByQueryAndSortedUseCase @Inject constructor(
    private val repository: CardsRepository,
) {
    operator fun invoke(
        typeClover: String,
        typeBrick: String,
        typePiqui: String,
        typeRedHeard: String,
    ) = repository.fetchImageBySorted(typeClover, typeBrick, typePiqui, typeRedHeard)
}