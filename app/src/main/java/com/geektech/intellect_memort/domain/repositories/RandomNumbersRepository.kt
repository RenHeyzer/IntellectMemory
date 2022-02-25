package com.geektech.intellect_memort.domain.repositories

import com.geektech.intellect_memort.common.resource.Resource
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import com.geektech.intellect_memort.presentation.state.UIState
import kotlinx.coroutines.flow.Flow

interface RandomNumbersRepository {
     fun generateRandomNumbers(quantity:Int): Flow<Resource<List<RandomNumbersModel>>>
}
