package com.geektech.intellect_memort.common.extension

import android.util.Log

fun Int.correctNumber(): Int {
    val quantityNumber: Int = this
    Log.e("quantum", "before $quantityNumber")
    var up = 0
    for (num in 0 until this) {
        if (num % 6 * 2 == 0) {
            up++
        }
    }
    Log.e("quantume", quantityNumber.plus(up).toString())
    return if ((quantityNumber + up) % 6 * 2 == 0) {
        quantityNumber + up
    } else {
        quantityNumber + up
    }
}