package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.answer

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.models.NumbersModel
import com.geektech.intellect_memort.domain.usecases.answernumbers.DeleteAllAnswerNumbersUseCase
import com.geektech.intellect_memort.domain.usecases.answernumbers.InsertAnswerNumberUseCase
import com.geektech.intellect_memort.domain.usecases.numbers.GetAllNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerNumbersViewModel @Inject constructor(
    private val insertAnswerNumberUseCase: InsertAnswerNumberUseCase,
    private val deleteAllAnswerNumbersUseCase: DeleteAllAnswerNumbersUseCase,
    private val getAllRandomNumbersUseCase: GetAllNumbersUseCase,
) : BaseViewModel() {

    init {
        deleteAllAnswerNumbers()
    }

    private val _randomNumbersState =
        MutableStateFlow<List<NumbersModel>>(emptyList())
    val randomNumbersState: StateFlow<List<NumbersModel>> = _randomNumbersState

    fun insertAllAnswerOfNumbers(numbers: List<AnswerNumbersModel>) = viewModelScope.launch {
        insertAnswerNumberUseCase.execute(numbers)
    }

    private fun deleteAllAnswerNumbers() = viewModelScope.launch {
        deleteAllAnswerNumbersUseCase.execute()
    }

    fun getAllRandomNumbers() = viewModelScope.launch {
        getAllRandomNumbersUseCase.execute().collect {
            _randomNumbersState.value = it
        }
    }
}