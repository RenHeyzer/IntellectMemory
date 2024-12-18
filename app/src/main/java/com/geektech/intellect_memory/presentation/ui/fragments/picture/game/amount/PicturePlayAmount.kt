package com.geektech.intellect_memory.presentation.ui.fragments.picture.game.amount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.databinding.FragmentPicturePlayAmountBinding

class PicturePlayAmount : Fragment(R.layout.fragment_picture_play_amount) {

    val binding by viewBinding(FragmentPicturePlayAmountBinding::bind)
    var amountFromPicker = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupNumberPicker()
    }

    private fun setupListeners() {
        binding.pictureAmountPicker.setOnValueChangedListener { currentVal, oldVar, newVal ->
            amountFromPicker = currentVal.value
        }
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                PicturePlayAmountDirections
                    .actionPicturePlayAmountToPicturePlayTime(amountFromPicker))
        }
    }


    private fun setupNumberPicker() {
        val nums: Array<String> = arrayOf("00-09", "10-19", "20-29", "30-39", "40-49", "50-59",
            "60-69", "70-79", "80-89", "90-99")
        binding.pictureAmountPicker.wrapSelectorWheel = false
        binding.pictureAmountPicker.displayedValues = nums
        binding.pictureAmountPicker.minValue = 0
        binding.pictureAmountPicker.maxValue = nums.size - 1
    }

}