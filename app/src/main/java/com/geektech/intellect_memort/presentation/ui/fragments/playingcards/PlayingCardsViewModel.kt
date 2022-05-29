package com.geektech.intellect_memort.presentation.ui.fragments.playingcards

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.usecases.FetchCardsByQueryAndSortedUseCase
import com.geektech.intellect_memort.domain.usecases.FetchCardsUseCase
import com.geektech.intellect_memort.domain.usecases.PassResultsUseCase
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.models.toUI
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayingCardsViewModel @Inject constructor(
    private val fetchCardsByQueryUseCase: FetchCardsByQueryAndSortedUseCase,
    private val fetchCardsUseCase: FetchCardsUseCase,
    private val passResultsUseCase: PassResultsUseCase,
) : BaseViewModel() {

    private val _fetchCardsState = MutableStateFlow<UIState<List<CardsUI>>>(UIState.Loading())
    val fetchCardsState: StateFlow<UIState<List<CardsUI>>> = _fetchCardsState

    private val _fetchCardsByQueryState = MutableStateFlow<UIState<List<CardsUI>>>(UIState.Loading())
    val fetchCardsByQueryState: StateFlow<UIState<List<CardsUI>>> = _fetchCardsByQueryState

    private val _showAnswerCardsState = MutableStateFlow<List<CardsUI>>(emptyList())
    val showAnswerCardsState = _showAnswerCardsState.asStateFlow()

    fun fetchImageOfCards(
        typeClover: String,
        typeBrick: String,
        typePiqui: String,
        typeRedHeard: String,
    ) {
        fetchCardsUseCase.invoke(typeClover, typeBrick, typePiqui, typeRedHeard)
            .collectRequest(_fetchCardsState) {
                it.shuffled().map { model ->
                    model.toUI()
                }
            }
    }

    fun fetchCards(
        typeClover: String,
        typeBrick: String,
        typePiqui: String,
        typeRedHeard: String,
    ) {
        fetchCardsByQueryUseCase.invoke(typeClover, typeBrick, typePiqui, typeRedHeard)
            .collectRequest(_fetchCardsByQueryState) {
                it.map { model ->
                    model.toUI()
                }

            }
    }

    fun passResults(points: Int) = viewModelScope.launch(Dispatchers.IO) {
        passResultsUseCase.execute(points)
    }

    fun showResults(list: List<CardsUI>) = viewModelScope.launch(Dispatchers.IO) {
        _showAnswerCardsState.value = list
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("viewModelka", "clear")
    }
}