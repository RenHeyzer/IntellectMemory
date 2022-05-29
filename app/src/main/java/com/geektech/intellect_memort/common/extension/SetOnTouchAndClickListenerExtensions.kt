package com.geektech.intellect_memort.common.extension

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MotionEvent
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton


@SuppressLint("ClickableViewAccessibility")
fun MaterialButton.setOnTouchListenerClickable(
    @IdRes actionId: Int? = null,
    direction: NavDirections? = null,
) {
    setOnTouchListener { _, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                this.isSelected = true
                this.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")))
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP,
            -> {
                if (actionId != null) {
                    findNavController().navigateSafely(actionId = actionId)
                }
                if (direction != null) {
                    findNavController().navigateSafely(directions = direction)
                }
                this.isSelected = false
                this.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF000000")))
            }
        }
        true
    }

}