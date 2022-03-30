package com.geektech.intellect_memort.presentation.ui.fragments.game

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import com.geektech.intellect_memort.domain.usecases.answernumbers.GetAllAnswerNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.randomnumbers.GetAllRandomNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultNumbersViewModel @Inject constructor(
    private val getAllAnswerNumbersUseCase: GetAllAnswerNumbersUseCase,
    private val getAllRandomNumbersUseCase: GetAllRandomNumbersUseCase
) : BaseViewModel() {

    private val _randomNumbersState = MutableStateFlow<List<RandomNumbersModel>>(emptyList())
    val randomNumbersState: StateFlow<List<RandomNumbersModel>> = _randomNumbersState

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
}