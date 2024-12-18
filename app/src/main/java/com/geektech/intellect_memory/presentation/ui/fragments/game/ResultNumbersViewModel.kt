package com.geektech.intellect_memory.presentation.ui.fragments.game

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memory.common.base.BaseViewModel
import com.geektech.intellect_memory.domain.models.AnswerNumbersModel
import com.geektech.intellect_memory.domain.models.NumbersModel
import com.geektech.intellect_memory.domain.usecases.PassResultsUseCase
import com.geektech.intellect_memory.domain.usecases.answernumbers.GetAllAnswerNumbersUseCase
import com.geektech.intellect_memory.domain.usecases.numbers.GetAllNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultNumbersViewModel @Inject constructor(
    private val getAllAnswerNumbersUseCase: GetAllAnswerNumbersUseCase,
    private val getAllRandomNumbersUseCase: GetAllNumbersUseCase,
    private val passResultsUseCase: PassResultsUseCase
) : BaseViewModel() {

    private val _randomNumbersState = MutableStateFlow<List<NumbersModel>>(emptyList())
    val randomNumbersState: StateFlow<List<NumbersModel>> = _randomNumbersState

    private val _answerNumbersState = MutableStateFlow<List<AnswerNumbersModel>>(emptyList())
    val answerNumbersState: StateFlow<List<AnswerNumbersModel>> = _answerNumbersState

    init {
        getAllRandomNumbers()
        getAllAnswerNumbers()
    }

    private fun getAllRandomNumbers() = viewModelScope.launch {
        getAllRandomNumbersUseCase.execute().collect {
            _randomNumbersState.value = it
        }
    }

    private fun getAllAnswerNumbers() = viewModelScope.launch {
        getAllAnswerNumbersUseCase.execute().collect {
            _answerNumbersState.value = it
        }
    }

    fun passResults(points: Int) = viewModelScope.launch(Dispatchers.IO) {
        passResultsUseCase.execute(points)
    }
}