package com.geektech.intellect_memort.presentation.ui.fragments.students.create

import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.usecases.CreateStudentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateStudentsViewModel @Inject constructor(
    private val createStudent: CreateStudentsUseCase,
) : BaseViewModel() {

    suspend fun createStudent(student: HashMap<String, Any>) {
        createStudent.execute(student)
    }
}