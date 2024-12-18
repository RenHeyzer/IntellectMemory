package com.geektech.intellect_memory.common.extension

import android.view.View
import com.geektech.intellect_memory.common.utils.OnSingleClickListener


fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

