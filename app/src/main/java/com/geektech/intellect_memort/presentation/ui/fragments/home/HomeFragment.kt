package com.geektech.intellect_memort.presentation.ui.fragments.home

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnTouchListenerClickable
import com.geektech.intellect_memort.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {
    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()

    override fun setupListeners() {
        binding.btnPicture.setOnTouchListenerClickable(R.id.action_homeFragment_to_pictureFragment)
        binding.btnRandomNumbers.setOnTouchListenerClickable(direction = HomeFragmentDirections.actionHomeFragmentToRandomNumbersFragment(false))
        binding.btnBinaryRandomNumbers.setOnTouchListenerClickable(direction = HomeFragmentDirections.actionHomeFragmentToRandomNumbersFragment(true))
        binding.btnPlayingCards.setOnTouchListenerClickable(R.id.action_homeFragment_to_playingCardsFragment)
        binding.btnRating.setOnTouchListenerClickable(R.id.action_homeFragment_to_ratingFragment)
    }
}