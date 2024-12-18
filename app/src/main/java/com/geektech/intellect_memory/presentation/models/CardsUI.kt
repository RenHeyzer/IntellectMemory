package com.geektech.intellect_memory.presentation.models

import android.os.Parcelable
import com.geektech.intellect_memory.common.base.IBaseDiffModel
import com.geektech.intellect_memory.domain.models.CardsModel
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class CardsUI(
    var url: String? = null,
    override var id: Int? = null,
    var type: String? = null,
    val emptySpace: Boolean? = null,
): Serializable, Parcelable, IBaseDiffModel

fun CardsModel.toUI() = CardsUI(url, id, type)