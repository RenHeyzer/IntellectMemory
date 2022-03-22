package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.answer

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import com.geektech.intellect_memort.domain.models.toAnswerNumbersModel
import com.geektech.intellect_memort.domain.usecases.answernumbers.DeleteAllAnswerNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.answernumbers.DeleteAnswerNumberUseCase
import com.geektech.intellect_memort.domain.usecases.answernumbers.GetAllAnswerNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.answernumbers.InsertAnswerNumberUseCase
import com.geektech.intellect_memort.domain.usecases.randomnumbers.GetAllRandomNumbersUseCase
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerRandomNumbersViewModel @Inject constructor(
    private val getAllAnswerNumbersUseCase: GetAllAnswerNumbersUseCase,
    private val insertAnswerNumberUseCase: InsertAnswerNumberUseCase,
    private val deleteAnswerNumberUseCase: DeleteAnswerNumberUseCase,
    private val deleteAllAnswerNumbersUseCase: DeleteAllAnswerNumbersUseCase,
    private val getAllRandomNumbersUseCase: GetAllRandomNumbersUseCase
) : BaseViewModel() {

    init {
        deleteAllAnswerNumbers()
    }

    private val _randomNumbersState =
        MutableStateFlow<List<RandomNumbersModel>>(emptyList())
    val randomNumbersState: StateFlow<List<RandomNumbersModel>> = _randomNumbersState

    private val _answerNumbersState =
        MutableStateFlow<List<AnswerNumbersModel>>(emptyList())
    val answerNumbersState: StateFlow<List<AnswerNumbersModel>> = _answerNumbersState

    fun getAllAnswerNumbers() = viewModelScope.launch {
        getAllAnswerNumbersUseCase.execute().collect {
            _answerNumbersState.value = it
        }
    }

    fun insertAnswerNumber(number: AnswerNumbersModel) = viewModelScope.launch {
        insertAnswerNumberUseCase.execute(number)
    }

    fun deleteAnswerNumber(number: AnswerNumbersModel) = viewModelScope.launch {
        deleteAnswerNumberUseCase.execute(number)
    }

    fun deleteAllAnswerNumbers() = viewModelScope.launch {
        deleteAllAnswerNumbersUseCase.execute()
    }

    fun getAllRandomNumbers() = viewModelScope.launch {
        getAllRandomNumbersUseCase.execute().collect {
            _randomNumbersState.value = it
        }
    }
}