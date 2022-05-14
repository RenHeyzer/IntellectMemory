package com.geektech.intellect_memort.presentation.models

import com.geektech.intellect_memort.domain.models.CardsModel
import java.io.Serializable

data class CardsUI(
    var url: String? = null,
    var id: Int? = null,
    var type: String? = null,
    val emptySpace: Boolean? = null,
): Serializable

fun CardsModel.toUI() = CardsUI(url, id, type)