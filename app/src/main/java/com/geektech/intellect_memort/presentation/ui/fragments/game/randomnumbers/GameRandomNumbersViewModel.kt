package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameRandomNumbersViewModel @Inject constructor() : BaseViewModel() {
     val list = ArrayList<RandomNumbersModel>()
    private val _randomNumbersState = MutableLiveData<ArrayList<RandomNumbersModel>>()
    val randomNumbersState: LiveData<ArrayList<RandomNumbersModel>> = _randomNumbersState
    fun createRandomNumbers(quantityNumbers: Int) {
        viewModelScope.launch {
            val random = List(quantityNumbers) { Random().nextInt(9) }
            var index = 0
            random.forEach {
                list.addAll(listOf(RandomNumbersModel(it, index)))
                _randomNumbersState.postValue(list)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        list.clear()
    }
}