package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.usecases.DeleteRandomNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.RandomNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.UploadRandomNumbersUseCase
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameRandomNumbersViewModel @Inject constructor(
    private val useCase: RandomNumbersUseCase,
    private val uploadUseCase: UploadRandomNumbersUseCase,
    private val deleteUseCase: DeleteRandomNumbersUseCase
) : BaseViewModel() {

    private val _randomNumbersState =
        MutableStateFlow<UIState<List<RandomNumbersModel>>>(UIState.Loading())
    val randomNumbersState: StateFlow<UIState<List<RandomNumbersModel>>> = _randomNumbersState

    init {
        generateRandomNumbers()
    }

    fun uploadRandomNumbers(quantity:Int) = viewModelScope.launch {
        uploadUseCase(quantity)
    }

    private fun generateRandomNumbers() {
        _randomNumbersState.subscribeTo {
            useCase()
        }
    }

    fun deleteRandomNumbers() = viewModelScope.launch {
        deleteUseCase()
    }
}