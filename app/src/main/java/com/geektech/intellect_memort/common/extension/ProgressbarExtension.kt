package com.geektech.intellect_memort.common.extension

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment

fun Fragment.showDialog(
    layout: Int,
): Dialog {
    val dialog = Dialog(requireContext())
    with(dialog) {
        setContentView(layout)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    return dialog
}