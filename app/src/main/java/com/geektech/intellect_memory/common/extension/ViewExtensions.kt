package com.geektech.intellect_memory.common.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.disableClickable() {
    this.isClickable = false
}

fun View.enableClickable() {
    this.isClickable = true
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
