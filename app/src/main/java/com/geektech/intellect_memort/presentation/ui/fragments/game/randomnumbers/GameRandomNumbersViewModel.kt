package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import androidx.lifecycle.viewModelScope
import com.geektech.intellect_memort.common.base.BaseViewModel
import com.geektech.intellect_memort.domain.usecases.RandomNumbersUseCase
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameRandomNumbersViewModel @Inject constructor(
    private val useCase: RandomNumbersUseCase
) : BaseViewModel() {

    val list = ArrayList<RandomNumbersModel>()
    private val _randomNumbersState = MutableStateFlow(list)
    val randomNumbersState: StateFlow<ArrayList<RandomNumbersModel>> = _randomNumbersState

    fun createRandomNumbers(quantityNumbers: Int) = viewModelScope.launch(Dispatchers.IO) {
        val random = List(quantityNumbers) { Random().nextInt(9) }
        val index = 0
        random.forEach {
            list.addAll(listOf(RandomNumbersModel(it, index)))
            _randomNumbersState.value = list
        }
    }

    suspend fun addRandomNumbers(item: HashMap<String, Any>) {
        useCase.invoke(item)
    }
    override fun onCleared() {
        super.onCleared()
        list.clear()
    }
}