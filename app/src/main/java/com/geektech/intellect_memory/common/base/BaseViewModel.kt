package com.geektech.intellect_memory.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memory.common.resource.Resource
import com.geektech.intellect_memory.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    protected fun <T> MutableLiveData<UIState<T>>.subscribeTo(
        request: () -> Flow<Resource<T>>,
    ) {
        viewModelScope.launch {
            request().collect {
                when (it) {
                    is Resource.Loading -> {
                        this@subscribeTo.value = UIState.Loading()
                    }
                    is Resource.Error -> it.message?.let { error ->
                        this@subscribeTo.value = UIState.Error(error)
                    }
                    is Resource.Success -> it.data?.let { data ->
                        this@subscribeTo.value = UIState.Success(data)
                    }
                }
            }
        }
    }

    protected fun <T, S> Flow<Resource<T>>.collectRequest(
        state: MutableStateFlow<UIState<S>>,
        mappedData: (T) -> S,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            this@collectRequest.collect {
                when (it) {
                    is Resource.Loading -> {
                        state.value = UIState.Loading()
                    }
                    is Resource.Error -> it.message?.let { error ->
                        state.value = UIState.Error(error)
                    }
                    is Resource.Success -> it.data?.let { data ->
                        state.value = UIState.Success(mappedData(data))
                    }
                }
            }
        }
    }

    protected fun <T> MutableStateFlow<UIState<T>>.subscribeTo(
        request: () -> Flow<Resource<T>>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            request().collect {
                when (it) {
                    is Resource.Loading -> {
                        this@subscribeTo.value = UIState.Loading()
                    }
                    is Resource.Error -> it.message?.let { error ->
                        this@subscribeTo.value = UIState.Error(error)
                    }
                    is Resource.Success -> it.data?.let { data ->
                        this@subscribeTo.value = UIState.Success(data)
                    }
                }
            }
        }
    }
}