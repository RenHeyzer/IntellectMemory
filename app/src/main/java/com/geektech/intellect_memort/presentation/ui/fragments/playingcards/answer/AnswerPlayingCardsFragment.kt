package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.answer

import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
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
    private val playingCardsAdapter = PlayingCardsAnsweringAdapter(listForPlayingCards, this, true)
    private val answerPlayingCardsAdapter =
        PlayingCardsAnsweringAdapter(listForAnswering, this, false)
    override val binding by viewBinding(FragmentAnswerPlayingCardsBinding::bind)
    override val viewModel: AnswerPlayingCardsViewModel by viewModels()
    private val args by navArgs<AnswerPlayingCardsFragmentArgs>()
    private var countDownTimer: CountDownTimer? = null
    override fun initialize() {

    }

    override fun setupListeners() {
    }

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
                    it.data.forEach {
                        listForAnswering.add(CardsUI("", 0, "", true))
                        listForPlayingCards.add(CardsUI(it.url, it.id, it.type, false))
                    }
                    initializeAdapters()
                }
            }
        }
    }

    private fun initializeAdapters() {
        binding.rvCards.run {
            adapter = playingCardsAdapter
            setOnDragListener(playingCardsAdapter.dragInstance)
        }
        binding.rvCardsAnswering.run {
            adapter = answerPlayingCardsAdapter
            setOnDragListener(answerPlayingCardsAdapter.dragInstance)
        }
    }

    override fun setEmptyList(visibility: Int, recyclerView: Int, emptyTextView2: Int) {
        requireActivity().findViewById<RecyclerView>(recyclerView).visibility = visibility
        requireActivity().findViewById<TextView>(emptyTextView2).visibility = visibility
    }

}