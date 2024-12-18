package com.geektech.intellect_memory.presentation.ui.fragments.registration.signin

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memory.common.base.BaseViewModel
import com.geektech.intellect_memory.domain.models.AdminModel
import com.geektech.intellect_memory.domain.models.StudentsModel
import com.geektech.intellect_memory.domain.usecases.SignInAdminUseCase
import com.geektech.intellect_memory.domain.usecases.SignInStudentUseCase
import com.geektech.intellect_memory.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInAdmin: SignInAdminUseCase,
    private val signInStudent: SignInStudentUseCase,
) : BaseViewModel() {

    private val _studentsState = MutableStateFlow<UIState<List<StudentsModel?>>>(UIState.Loading())
    val studentsState: StateFlow<UIState<List<StudentsModel?>>> = _studentsState

    private val _adminsState = MutableStateFlow<UIState<List<AdminModel?>>>(UIState.Loading())
    val adminsState: StateFlow<UIState<List<AdminModel?>>> = _adminsState

    init {
        signInStudents()
        signInAdmins()
    }

    private fun signInStudents() = viewModelScope.launch {
        _studentsState.subscribeTo {
            signInStudent()
        }
    }

    private fun signInAdmins() = viewModelScope.launch {
        _adminsState.subscribeTo {
            signInAdmin()
        }
    }
}