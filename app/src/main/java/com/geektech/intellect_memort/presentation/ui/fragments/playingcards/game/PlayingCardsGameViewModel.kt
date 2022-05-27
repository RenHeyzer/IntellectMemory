package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.game

import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.usecases.FetchCardsUseCase
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.models.toUI
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayingCardsGameViewModel @Inject constructor(
    private val fetchCardsUseCase: FetchCardsUseCase,
) : BaseViewModel() {
    private val _fetchCardsState = MutableStateFlow<UIState<List<CardsUI>>>(UIState.Loading())
    val fetchCardsState: StateFlow<UIState<List<CardsUI>>> = _fetchCardsState

    fun fetchImageOfCards(
        typeClover: String,
        typeBrick: String,
        typePiqui: String,
        typeRedHeard: String,
    ) {
        fetchCardsUseCase(
            typeClover,
            typeBrick,
            typePiqui,
            typeRedHeard
        ).collectRequest(_fetchCardsState) { it ->
            it.shuffled().map {
                it.toUI()
            }
        }
    }

}