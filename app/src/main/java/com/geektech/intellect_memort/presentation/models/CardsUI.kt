package com.geektech.intellect_memort.presentation.models

import com.geektech.intellect_memort.domain.models.CardsModel

data class CardsUI(
    var url: String? = null,
    var id: String? = null,
    var type: String? = null,
)

fun CardsModel.toUI() = CardsUI(url, id, type)