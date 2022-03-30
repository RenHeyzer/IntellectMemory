package com.geektech.intellect_memort.common.extension

import android.os.CountDownTimer
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.geektech.intellect_memort.R
import java.text.DecimalFormat
import java.text.NumberFormat

fun Fragment.timer(textView: TextView, timeMills: Int, funOnFinish: () -> Unit): CountDownTimer {
    val timer = object : CountDownTimer((timeMills * 60000).toLong(), 1000) {
        val decimal: NumberFormat = DecimalFormat("00")

        override fun onTick(millisUntilFinished: Long) {
            val minute = (millisUntilFinished / 1000) / 60
            val seconds = (millisUntilFinished / 1000) % 60

            textView.text =
                getString(
                    R.string.timer_template,
                    decimal.format(minute).toString(),
                    decimal.format(seconds).toString()
                )
        }

        override fun onFinish() {
            funOnFinish()
        }
    }
    return timer
}