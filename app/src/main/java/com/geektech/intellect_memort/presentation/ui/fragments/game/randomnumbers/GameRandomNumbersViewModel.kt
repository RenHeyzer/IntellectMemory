package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.usecases.RandomNumbersUseCase
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GameRandomNumbersViewModel @Inject constructor(
    private val useCase: RandomNumbersUseCase,
) : BaseViewModel() {

    private val _randomNumbersState =
        MutableStateFlow<UIState<List<RandomNumbersModel>>>(UIState.Loading())
    val randomNumbersState: StateFlow<UIState<List<RandomNumbersModel>>> = _randomNumbersState

    fun generateRandomNumbers(quantity: Int) {
        _randomNumbersState.subscribeTo {
            useCase(quantity)
        }
    }
}