package com.geektech.intellect_memory.presentation.ui.fragments.students.list

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
class ListOfStudentsViewModel @Inject constructor(
    private val repository: ListOfStudentsRepository,
) : BaseViewModel() {

    private val _studentsState = MutableStateFlow<PagingData<StudentsModel>>(PagingData.empty())
    val studentsState: StateFlow<PagingData<StudentsModel>> = _studentsState

    init {
        getListOfStudents()
        listenStudents()
    }

    private fun getListOfStudents() = viewModelScope.launch {
        repository.getListOfStudents(null).cachedIn(viewModelScope).collect {
            _studentsState.value = it
        }
    }

    private fun listenStudents() {
        repository.studentsCollection.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                val list: ArrayList<StudentsModel> = ArrayList()
                val data = PagingData.from(list)
                value.forEach {
                    val message = it.toObject(StudentsModel::class.java)
                    list.add(message)
                }
                _studentsState.value = data
            }
        }
    }
}