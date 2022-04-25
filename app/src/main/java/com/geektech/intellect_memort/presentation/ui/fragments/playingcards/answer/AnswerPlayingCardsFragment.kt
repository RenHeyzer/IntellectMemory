package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.answer

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentAnswerPlayingCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerPlayingCardsFragment :
    BaseFragment<FragmentAnswerPlayingCardsBinding, AnswerPlayingCardsViewModel>(
        R.layout.fragment_answer_playing_cards
    ) {
    override val binding by viewBinding(FragmentAnswerPlayingCardsBinding::bind)
    override val viewModel: AnswerPlayingCardsViewModel by viewModels()

}