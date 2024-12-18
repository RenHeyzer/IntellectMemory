package com.geektech.intellect_memory.presentation.ui.fragments.raiting

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.geektech.intellect_memory.common.base.BaseViewModel
import com.geektech.intellect_memory.data.repositories.ListOfStudentsRepository
import com.geektech.intellect_memory.domain.models.StudentsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val repository: ListOfStudentsRepository,
) : BaseViewModel() {

    private val _studentsState = MutableStateFlow<PagingData<StudentsModel>>(PagingData.empty())
    val studentsState: StateFlow<PagingData<StudentsModel>> = _studentsState

    fun getListOfStudents(school: String?) = viewModelScope.launch {
        repository.getListOfStudents(school).cachedIn(viewModelScope).collect {
            _studentsState.value = it
        }
    }

    fun searchStudents(fullName: String, school: String?) =
        repository.searchStudents(fullName, school).cachedIn(viewModelScope)
}