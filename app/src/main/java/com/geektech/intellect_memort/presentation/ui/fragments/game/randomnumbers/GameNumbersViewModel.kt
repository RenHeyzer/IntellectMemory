package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.models.NumbersModel
import com.geektech.intellect_memort.domain.usecases.numbers.*
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameNumbersViewModel @Inject constructor(
    private val useCase: NumbersUseCase,
    private val binaryUseCase: BinaryNumbersUseCase,
    private val getAllRandomNumbersUseCase: GetAllNumbersUseCase,
    private val insertAllRandomNumbersUseCase: InsertAllNumbersUseCase,
    private val deleteAllRandomNumbersUseCase: DeleteAllNumbersUseCase,
    private val uploadRandomNumbersUseCase: UploadNumbersUseCase,
) : BaseViewModel() {

    init {
        deleteAllRandomNumbers()
    }

    private val _randomNumbersState =
        MutableStateFlow<UIState<List<NumbersModel>>>(UIState.Loading())
    val randomNumbersState: StateFlow<UIState<List<NumbersModel>>> = _randomNumbersState

    private val _saveRandomNumbersState =
        MutableStateFlow<List<NumbersModel>>(emptyList())
    val saveRandomNumbersState: StateFlow<List<NumbersModel>> =
        _saveRandomNumbersState

    fun generateRandomNumbers(quantitynumber: Int) = viewModelScope.launch {
        _randomNumbersState.subscribeTo {
            useCase(quantitynumber)
        }
    }

    fun generateBinaryNumbers(quantitynumber: Int) = viewModelScope.launch {
        _randomNumbersState.subscribeTo {
            binaryUseCase.execute(quantitynumber)
        }
    }

    fun getAllRandomNumbers() = viewModelScope.launch {
        getAllRandomNumbersUseCase.execute().collect {
            _saveRandomNumbersState.value = it
        }
    }

    fun insertAllRandomNumbers(numbers: List<NumbersModel>) = viewModelScope.launch {
        insertAllRandomNumbersUseCase.execute(numbers)
    }

    fun deleteAllRandomNumbers() = viewModelScope.launch {
        deleteAllRandomNumbersUseCase.execute()
    }

    fun uploadRandomNumbers(quantitynumber: Int) = viewModelScope.launch {
        uploadRandomNumbersUseCase.invoke(quantitynumber)
    }
}