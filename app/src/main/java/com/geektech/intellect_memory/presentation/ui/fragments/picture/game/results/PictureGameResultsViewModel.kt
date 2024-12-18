package com.geektech.intellect_memory.presentation.ui.fragments.picture.game.results

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memory.common.base.BaseViewModel
import com.geektech.intellect_memory.domain.usecases.PassResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureGameResultsViewModel @Inject constructor(
    private val passResultsUseCase: PassResultsUseCase,
) : BaseViewModel() {

    fun passResults(points: Int) = viewModelScope.launch {
        passResultsUseCase.execute(points)
    }
}