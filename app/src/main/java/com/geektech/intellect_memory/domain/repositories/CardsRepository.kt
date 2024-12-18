package com.geektech.intellect_memory.domain.repositories

import com.geektech.intellect_memory.common.resource.Resource
import com.geektech.intellect_memory.domain.models.CardsModel
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun fetchImageOfCards(
        typeClover: String?,
        typeBrick: String?,
        typePiqui: String?,
        typeRedHeard: String?,
    ): Flow<Resource<List<CardsModel>>>

    fun fetchImageBySorted(
        typeClover: String?,
        typeBrick: String?,
        typePiqui: String?,
        typeRedHeard: String?,
    ): Flow<Resource<List<CardsModel>>>

}