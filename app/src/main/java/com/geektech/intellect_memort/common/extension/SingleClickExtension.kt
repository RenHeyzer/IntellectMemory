package com.geektech.intellect_memort.common.extension

import android.view.View
import com.geektech.intellect_memort.common.utils.OnSingleClickListener


fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

