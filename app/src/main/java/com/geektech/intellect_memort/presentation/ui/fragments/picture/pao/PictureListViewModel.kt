package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao

import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.data.remote.PictureRepository
import com.geektech.intellect_memort.domain.model.PictureImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val repo: PictureRepository,
) : BaseViewModel() {


    fun fetchimages(): ArrayList<PictureImageModel> = repo.fetchImagesDefault()

}

