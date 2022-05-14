package com.geektech.intellect_memort.common.extension

fun <T, N> ArrayList<T>.doubleList(list: List<N>, data: (data: Pair<T, N>) -> Unit) {
    this.asSequence().zip(list.asSequence()).forEach {
        data(it)
    }
}