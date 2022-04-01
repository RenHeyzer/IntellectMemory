package com.geektech.intellect_memort.presentation.ui.fragments.picture.game.countdown

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.databinding.FragmentPicturePlayCountDownBinding

class PicturePlayCountDown : Fragment(R.layout.fragment_picture_play_count_down) {

    private val binding by viewBinding(FragmentPicturePlayCountDownBinding::bind)
    private val args: PicturePlayCountDownArgs by navArgs()

    // game vars
    private val timeForCountDownInSec = 5


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        timer()
        initialize()
    }

    private fun initialize() {
        sealingBackPress()
    }

    private fun setupListeners() {}

    private fun timer() {
        val timer = object : CountDownTimer((timeForCountDownInSec * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.countdownText.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                findNavController()
                    .navigate(PicturePlayCountDownDirections
                        .actionPicturePlayCountDownToPicturePlayGame(args.rangeNumber,
                            args.timeNumber))
            }
        }
        timer.start()
    }

    private fun sealingBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // on back pressed null
                }
            })
    }
}
