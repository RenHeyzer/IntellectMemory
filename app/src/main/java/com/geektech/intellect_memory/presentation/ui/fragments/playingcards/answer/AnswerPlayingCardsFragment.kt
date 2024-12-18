package com.geektech.intellect_memory.presentation.ui.fragments.playingcards.answer

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.base.BaseFragment
import com.geektech.intellect_memory.common.extension.navigateSafely
import com.geektech.intellect_memory.common.extension.overrideOnBackPressed
import com.geektech.intellect_memory.common.extension.setOnSingleClickListener
import com.geektech.intellect_memory.common.extension.timer
import com.geektech.intellect_memory.common.utils.PlayingCardsAnsweringListener
import com.geektech.intellect_memory.databinding.FragmentAnswerPlayingCardsBinding
import com.geektech.intellect_memory.presentation.models.CardsUI
import com.geektech.intellect_memory.presentation.state.UIState
import com.geektech.intellect_memory.presentation.ui.adapters.PlayingCardsAnsweringAdapter
import com.geektech.intellect_memory.presentation.ui.fragments.playingcards.PlayingCardsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerPlayingCardsFragment :
    BaseFragment<FragmentAnswerPlayingCardsBinding, PlayingCardsViewModel>(
        R.layout.fragment_answer_playing_cards
    ), PlayingCardsAnsweringListener {
    override val binding by viewBinding(FragmentAnswerPlayingCardsBinding::bind)
    private var scrollDistance = 0
    private var listForPlayingCards = arrayListOf<CardsUI>()
    private var listForAnswering = arrayListOf<CardsUI>()
    private var playingCardsAdapter: PlayingCardsAnsweringAdapter? = null
    private var answeringCardsAdapter: PlayingCardsAnsweringAdapter? = null
    override val viewModel: PlayingCardsViewModel by hiltNavGraphViewModels(R.id.main_graph)
    private val args by navArgs<AnswerPlayingCardsFragmentArgs>()
    private var countDwnTimer: CountDownTimer? = null
    private var ascendingTimerAsString = ""
    private var isStop = false

    override fun initialize() {
        setupPrefetching()
        setupAdapters()
    }

    private fun setupPrefetching() {
        val layoutManager = binding.rvCards.layoutManager as GridLayoutManager
        layoutManager.isItemPrefetchEnabled = true
        layoutManager.initialPrefetchItemCount = args.numbersOfCards
    }

    private fun setupAdapters() {
        playingCardsAdapter = PlayingCardsAnsweringAdapter(listForPlayingCards,
            this,
            this::showMistake)

        answeringCardsAdapter = PlayingCardsAnsweringAdapter(listForAnswering,
            this,
            this::showMistake)
    }

    override fun setupViews() {
        setupTimer()
    }

    private fun setupTimer() {
        countDwnTimer = timer(
            binding.txtTimer,
            args.time,
            {
                ascendingTimerAsString = it
            }, {
                if (ascendingTimerAsString.isNotEmpty()) {
                    answeringCardsAdapter?.let {
                        viewModel.showResults(it.getList() as List<CardsUI>)
                        findNavController().navigateSafely(
                            AnswerPlayingCardsFragmentDirections.actionAnswerPlayingCardsFragmentToCardsResultFragment(
                                time = ascendingTimerAsString,
                                numbersOfCards = args.numbersOfCards,
                                memoryList = args.memoryList,
                                anwerList = it.getList().toTypedArray(),
                                memorizationTimeOfAllCards = args.memorizationTimeOfAllCards
                            )
                        )
                    }
                }
            }
        )
    }

    override fun setupListeners() {
        setupScrollChecked()
        setupBack()
        setupFinish()
    }

    private fun setupScrollChecked() {
        playingCardsAdapter?.setOnScrollView(binding.autoScrollView)
        answeringCardsAdapter?.setOnScrollView(binding.autoScrollView)
        binding.autoScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (view != null) {
                scrollDistance = binding.autoScrollView.scrollY
                answeringCardsAdapter?.getScrollDistance(scrollDistance)
                playingCardsAdapter?.getScrollDistance(scrollDistance)
            }
        }
    }

    private fun setupBack() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateSafely(
                AnswerPlayingCardsFragmentDirections.actionAnswerPlayingCardsFragmentToExitDialogFragment(
                    true
                )
            )
        }

        overrideOnBackPressed {
            findNavController().navigateSafely(AnswerPlayingCardsFragmentDirections
                .actionAnswerPlayingCardsFragmentToExitDialogFragment(
                    false
                ))
        }
    }

    private fun setupFinish() {
        binding.btnFinish.setOnSingleClickListener {
            Log.e("list", answeringCardsAdapter?.getList().toString())
            answeringCardsAdapter?.let {
                viewModel.showResults(it.getList() as List<CardsUI>)
                findNavController().navigateSafely(
                    AnswerPlayingCardsFragmentDirections.actionAnswerPlayingCardsFragmentToCardsResultFragment(
                        time = ascendingTimerAsString,
                        numbersOfCards = args.numbersOfCards,
                        memoryList = args.memoryList,
                        anwerList = it.getList().toTypedArray(),
                        memorizationTimeOfAllCards = args.memorizationTimeOfAllCards
                    )
                )
            }
        }
    }

    override fun setupObserves() {
        setUpObserveFetchCards()
    }

    private fun setUpObserveFetchCards() {
        viewModel.fetchCardsByQueryState.subscribe {
            when (it) {
                is UIState.Error -> {
                    Log.e("anime", "Error Fetch Cards Answer Fragment ${it.error}")
                }
                is UIState.Loading -> {
                    Log.e("anime", "Loading Fetch Cards Answer Fragment $it")
                }
                is UIState.Success -> {
                    Log.e("anime", "Success Fetch Cards Answer Fragment ${it.data}")
                    if (!isStop) {
                        countDwnTimer?.start()
                        it.data.forEach { card ->
                            listForAnswering.add(CardsUI("", null, null, true))
                            listForPlayingCards.add(CardsUI(card.url, card.id, card.type, false))
                        }
                        initializeAdapters()
                    }
                }
            }
        }
    }

    private fun initializeAdapters() {
        playingCardsAdapter?.let {
            binding.rvCards.run {
                adapter = playingCardsAdapter
                setOnDragListener(it.dragInstance)
            }
        }
        answeringCardsAdapter?.let {
            binding.rvCardsAnswering.run {
                adapter = answeringCardsAdapter
                setOnDragListener(it.dragInstance)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isStop = true
    }

    private fun showMistake() {
        Toast.makeText(requireContext(), "Этот слот занят!", Toast.LENGTH_SHORT).show()
    }

    override fun setEmptyList(visibility: Boolean) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollDistance = 0
        countDwnTimer?.cancel()
        countDwnTimer = null
        playingCardsAdapter = null
        answeringCardsAdapter = null
    }
}