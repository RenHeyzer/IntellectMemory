package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.answer

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.utils.PlayingCardsAnsweringListener
import com.geektech.intellect_memort.databinding.FragmentAnswerPlayingCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.state.UIState
import com.geektech.intellect_memort.presentation.ui.adapters.PlayingCardsAnsweringAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerPlayingCardsFragment :
    BaseFragment<FragmentAnswerPlayingCardsBinding, AnswerPlayingCardsViewModel>(
        R.layout.fragment_answer_playing_cards
    ), PlayingCardsAnsweringListener {
    private var listForPlayingCards = arrayListOf<CardsUI>()
    private var listForAnswering = arrayListOf<CardsUI>()
    override val binding by viewBinding(FragmentAnswerPlayingCardsBinding::bind)
    override val viewModel: AnswerPlayingCardsViewModel by viewModels()
    private val args by navArgs<AnswerPlayingCardsFragmentArgs>()
    private var countDwnTimer: CountDownTimer? = null

    override fun setupRequests() {
        viewModel.fetchCards(
            args.isClover,
            args.isbrick,
            args.ispiqui,
            args.isredHeart
        )
    }

    override fun setupObserves() {
        setUpObserveFetchCards()
    }

    private fun setUpObserveFetchCards() {
        viewModel.fetchCardsState.subscribe {
            when (it) {
                is UIState.Error -> {
                    Log.e("anime", "Error Fetch Cards Answer Fragment ${it.error}")
                }
                is UIState.Loading -> {
                    Log.e("anime", "Loading Fetch Cards Answer Fragment $it")
                }
                is UIState.Success -> {
                    Log.e("anime", "Success Fetch Cards Answer Fragment ${it.data}")
                    it.data.forEach { card ->
                        listForAnswering.add(CardsUI("", null, null, true))
                        listForPlayingCards.add(CardsUI(card.url, card.id, card.type, false))
                    }
                    initializeAdapters()
                }
            }
        }
    }

    private fun initializeAdapters() {
        val playingCardsAdapter =
            PlayingCardsAnsweringAdapter(listForPlayingCards, this, this::showMistake)
        val answeringAdapter =
            PlayingCardsAnsweringAdapter(listForAnswering, this, this::showMistake)
        binding.rvCards.run {
            adapter = playingCardsAdapter
            setOnDragListener(playingCardsAdapter.dragInstance)
        }
        binding.rvCardsAnswering.run {
            adapter = answeringAdapter
            setOnDragListener(answeringAdapter.dragInstance)
        }
    }

    private fun showMistake() {
        Toast.makeText(requireContext(), "Этот слот занят!", Toast.LENGTH_SHORT).show()
    }

    override fun setEmptyList(visibility: Boolean) {

    }
}