package com.geektech.intellect_memort.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_numbers_model")
data class AnswerNumbersModel(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "answer_number")
    var answerNumber: Int? = null,
)
