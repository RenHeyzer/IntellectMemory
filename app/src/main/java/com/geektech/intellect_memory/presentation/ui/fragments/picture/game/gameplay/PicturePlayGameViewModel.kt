package com.geektech.intellect_memory.presentation.ui.fragments.picture.game.gameplay

import com.geektech.intellect_memory.common.base.BaseViewModel
import com.geektech.intellect_memory.domain.models.PictureImageModel
import com.geektech.intellect_memory.domain.usecases.FetchPictureUseCase
import com.geektech.intellect_memory.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PicturePlayGameViewModel @Inject constructor(
    private val fetchPicture: FetchPictureUseCase,
) : BaseViewModel() {

    private val _picturesState =
        MutableStateFlow<UIState<List<PictureImageModel>>>(UIState.Loading())
    val picturesState: StateFlow<UIState<List<PictureImageModel>>> = _picturesState

    init {
        fetchImages()
    }

    private fun fetchImages() {
        _picturesState.subscribeTo {
            fetchPicture.execute()
        }
    }

}