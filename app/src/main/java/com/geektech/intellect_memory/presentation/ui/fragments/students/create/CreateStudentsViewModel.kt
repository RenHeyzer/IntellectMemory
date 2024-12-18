package com.geektech.intellect_memory.presentation.ui.fragments.students.create

import com.geektech.intellect_memory.common.base.BaseViewModel
import com.geektech.intellect_memory.domain.usecases.CreateStudentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateStudentsViewModel @Inject constructor(
    private val createStudent: CreateStudentsUseCase,
) : BaseViewModel() {

    suspend fun createStudent(student: HashMap<String, Any>, id: String) {
        createStudent.execute(student, id)
    }
}