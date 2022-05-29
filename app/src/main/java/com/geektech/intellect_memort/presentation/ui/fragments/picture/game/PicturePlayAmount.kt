package com.geektech.intellect_memort.presentation.ui.fragments.picture.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.databinding.FragmentPicturePlayAmountBinding

class PicturePlayAmount : Fragment(R.layout.fragment_picture_play_amount) {

    val binding by viewBinding(FragmentPicturePlayAmountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setupListeners() {
        binding.btnNext.setOnClickListener {

        }
    }

}