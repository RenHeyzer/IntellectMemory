package com.geektech.intellect_memort.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geektech.intellect_memort.common.base.IBaseDiffModel

@Entity(tableName = "result_numbers_model")
data class ResultNumbersModel(
    @PrimaryKey
    override val id: Int? = null,

    @ColumnInfo(name = "number")
    val number: Int? = null,

    @ColumnInfo(name = "answer_number")
    val answerNumber: Int? = null
) : IBaseDiffModel