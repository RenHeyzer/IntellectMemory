package com.geektech.intellect_memort.ui.fragments.playingcards

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPlayingCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingCardsFragment : BaseFragment<FragmentPlayingCardsBinding, PlayingCardsViewModel>(
    R.layout.fragment_playing_cards
) {
    override val binding by viewBinding(FragmentPlayingCardsBinding::bind)
    override val viewModel: PlayingCardsViewModel by viewModels()

}