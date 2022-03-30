package com.geektech.intellect_memort.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geektech.intellect_memort.common.base.IBaseDiffModel

@Entity(tableName = "random_numbers_model")
data class RandomNumbersModel(
    @PrimaryKey
    override val id: Int? = null,
    var number: Int? = null,
) : IBaseDiffModel