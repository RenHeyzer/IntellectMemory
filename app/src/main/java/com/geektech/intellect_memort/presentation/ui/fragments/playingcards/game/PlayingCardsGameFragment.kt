package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.game

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPlayingCardsGameBinding
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.state.UIState
import com.geektech.intellect_memort.presentation.ui.adapters.CardsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingCardsGameFragment :
    BaseFragment<FragmentPlayingCardsGameBinding, PlayingCardsGameViewModel>(
        R.layout.fragment_playing_cards_game
    ) {
    override val binding by viewBinding(FragmentPlayingCardsGameBinding::bind)
    override val viewModel: PlayingCardsGameViewModel by viewModels()
    private val args: PlayingCardsGameFragmentArgs by navArgs()
    private val adapter = CardsAdapter()
    private var currentPosition: Int = 0
    private val emptyList = ArrayList<CardsUI>()
    override fun initialize() {
        binding.rvCards.adapter = adapter
        when (args.numbersOfCards) {
            1 -> {
                binding.imgCards1.visibility = GONE
                binding.imgCards3.visibility = GONE
            }
            3 -> {
                binding.imgCards1.visibility = VISIBLE
                binding.imgCards3.visibility = VISIBLE
            }
        }
    }

    override fun setupRequests() {
        viewModel.fetchImageOfCards("clover", "", "", "")
    }

    override fun setupObserves() {
        viewModel.fetchCardsState.subscribe { uiState ->
            when (uiState) {
                is UIState.Error -> {
                    Log.e("anime", "Error Cards: ${uiState.error}")
                }
                is UIState.Loading -> {
                    Log.e("anime", "Loading Cards: $uiState")
                }
                is UIState.Success -> {
                    val arrayList = ArrayList<CardsUI>()
                }
            }

        }
    }
}

