package com.geektech.intellect_memort.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {
    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()


    override fun setupListeners() {
        setUpBtnPictureListener()
        setUpBtnRandomNumberListener()
        setUpBtnBinaryNumbersListener()
        setUpBtnPlayingCardsListener()
    }

    private fun setUpBtnPictureListener() {
        binding.btnPicture.setOnSingleClickListener {
            findNavController().navigate(R.id.pictureFragment)
        }
    }

    private fun setUpBtnRandomNumberListener() {
        binding.btnRandomNumbers.setOnSingleClickListener {
            findNavController().navigate(R.id.randomNumbersFragment)
        }
    }

    private fun setUpBtnBinaryNumbersListener() {
        binding.btnBinaryRandomNumbers.setOnSingleClickListener {
            findNavController().navigate(R.id.binaryNumbersFragment)
        }
    }

    private fun setUpBtnPlayingCardsListener() {
        binding.btnPlayingCards.setOnSingleClickListener {
            findNavController().navigate(R.id.playingCardsFragment)
        }
    }
}