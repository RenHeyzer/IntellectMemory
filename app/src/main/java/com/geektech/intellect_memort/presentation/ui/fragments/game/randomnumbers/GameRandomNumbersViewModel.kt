package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import com.geektech.intellect_memort.domain.usecases.randomnumbers.DeleteAllRandomNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.randomnumbers.GetAllRandomNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.randomnumbers.InsertAllRandomNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.randomnumbers.RandomNumbersUseCase
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameRandomNumbersViewModel @Inject constructor(
    private val useCase: RandomNumbersUseCase,
    private val getAllRandomNumbersUseCase: GetAllRandomNumbersUseCase,
    private val insertAllRandomNumbersUseCase: InsertAllRandomNumbersUseCase,
    private val deleteAllRandomNumbersUseCase: DeleteAllRandomNumbersUseCase,
) : BaseViewModel() {

    init {
        deleteAllRandomNumbers()
    }

    private val _randomNumbersState =
        MutableStateFlow<UIState<List<RandomNumbersModel>>>(UIState.Loading())
    val randomNumbersState: StateFlow<UIState<List<RandomNumbersModel>>> = _randomNumbersState

    private val _saveRandomNumbersState =
        MutableStateFlow<List<RandomNumbersModel>>(emptyList())
    val saveRandomNumbersState: StateFlow<List<RandomNumbersModel>> =
        _saveRandomNumbersState

    fun generateRandomNumbers(quantitynumber: Int) = viewModelScope.launch {
        _randomNumbersState.subscribeTo {
            useCase(quantitynumber)
        }
    }

    fun getAllRandomNumbers() = viewModelScope.launch {
        getAllRandomNumbersUseCase.execute().collect {
            _saveRandomNumbersState.value = it
        }
    }

    fun insertAllRandomNumbers(numbers: List<RandomNumbersModel>) = viewModelScope.launch {
        insertAllRandomNumbersUseCase.execute(numbers)
    }

    fun deleteAllRandomNumbers() = viewModelScope.launch {
        deleteAllRandomNumbersUseCase.execute()
    }
}