package com.geektech.intellect_memort.presentation.models

import com.geektech.intellect_memort.domain.models.CardsModel

data class CardsUI(
    val url: String? = null,
    val id: String? = null,
    val type: String? = null,
)

fun CardsModel.toUI() = CardsUI(url, id, type)