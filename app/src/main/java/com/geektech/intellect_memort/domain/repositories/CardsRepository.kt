package com.geektech.intellect_memort.domain.repositories

import com.geektech.intellect_memort.common.resource.Resource
import com.geektech.intellect_memort.domain.models.CardsModel
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun fetchImageOfCards(
        typeClover: String?,
        typeBrick: String?,
        typePiqui: String?,
        typeRedHeard: String?,
    ): Flow<Resource<List<CardsModel>>>
}