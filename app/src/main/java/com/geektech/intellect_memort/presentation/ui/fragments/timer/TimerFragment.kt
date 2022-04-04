package com.geektech.intellect_memort.presentation.ui.fragments.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.intellect_memort.databinding.FragmentTimerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        setUpTickTimer()
        return binding.root
    }

    private fun setUpTickTimer() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtTimer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                findNavController().navigateUp()
            }
        }
        timer.start()
    }
}