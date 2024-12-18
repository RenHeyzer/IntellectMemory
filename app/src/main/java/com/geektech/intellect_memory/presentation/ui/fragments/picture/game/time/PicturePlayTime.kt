package com.geektech.intellect_memory.presentation.ui.fragments.picture.game.time

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.databinding.FragmentPicturePlayTimeBinding

class PicturePlayTime : Fragment(R.layout.fragment_picture_play_time) {

    private val binding by viewBinding(FragmentPicturePlayTimeBinding::bind)
    private val args: PicturePlayTimeArgs by navArgs()
    private var timeNumber: Int? = null
    private var maxMinutesPerGame : Int = 60

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        initialize()
    }

    private fun initialize() {
        binding.timeEditText.addTextChangedListener { time ->
            if (time.toString().isNotEmpty()) {
                timeNumber = time.toString().trim().toInt()
            }
        }
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            if (timeNumber != null && timeNumber != 0
                && timeNumber!! <= maxMinutesPerGame) {
                findNavController()
                    .navigate(PicturePlayTimeDirections
                        .actionPicturePlayTimeToPicturePlayCountDown(args.rangeNumber,
                            timeNumber!!))
            } else {
                Toast.makeText(requireContext(), "Введите время от 1 до 60 минут", Toast.LENGTH_SHORT).show()
            }
        }
    }

}