package com.geektech.intellect_memort.common.extension


import android.os.CountDownTimer
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*
import java.util.concurrent.TimeUnit


fun Fragment.timerInSeconds(
    textView: TextView,
    millisInFuture: Long,
    countDownInterval: Long,
    funOnFinish: () -> Unit,
): CountDownTimer {
    return object : CountDownTimer(millisInFuture * 1000, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            val timeRemaining = timeString(millisUntilFinished)
            textView.text = timeRemaining
        }

        override fun onFinish() {
            textView.text = "00:00"
            funOnFinish()
        }
    }
}


private fun timeString(millisUntilFinished: Long): String {
    var millisUntilFinished: Long = millisUntilFinished
    val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
    millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

    val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

    val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

    // Format the string
    return String.format(
        Locale.getDefault(),
        "%02d:%02d",
        minutes, seconds
    )
}
