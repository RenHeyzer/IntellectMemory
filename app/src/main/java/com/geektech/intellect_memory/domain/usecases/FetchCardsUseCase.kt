package com.geektech.intellect_memory.domain.usecases

import com.geektech.intellect_memory.domain.repositories.CardsRepository
import javax.inject.Inject

class FetchCardsUseCase @Inject constructor(
    private val repository: CardsRepository,
) {
    operator fun invoke(
        typeClover: String,
        typeBrick: String,
        typePiqui: String,
        typeRedHeard: String,
    ) = repository.fetchImageOfCards(typeClover, typeBrick, typePiqui, typeRedHeard)
}