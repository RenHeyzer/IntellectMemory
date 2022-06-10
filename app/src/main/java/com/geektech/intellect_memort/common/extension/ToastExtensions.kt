package com.geektech.intellect_memort.common.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, duration).show()
}