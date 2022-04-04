package com.geektech.intellect_memort.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_numbers_model")
data class RandomNumbersModel(
    @PrimaryKey
    val id: Int? = null,
    var number: Int? = null,
)

fun RandomNumbersModel.toAnswerNumbersModel(): AnswerNumbersModel = AnswerNumbersModel(id, number)

