package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.game

import android.os.CountDownTimer
import android.util.Log
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.*
import com.geektech.intellect_memort.databinding.FragmentPlayingCardsGameBinding
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.state.UIState
import com.geektech.intellect_memort.presentation.ui.adapters.CardsAdapter
import com.geektech.intellect_memort.presentation.ui.fragments.playingcards.PlayingCardsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class PlayingCardsGameFragment :
    BaseFragment<FragmentPlayingCardsGameBinding, PlayingCardsViewModel>(
        R.layout.fragment_playing_cards_game
    ) {
    override val binding by viewBinding(FragmentPlayingCardsGameBinding::bind)
    override val viewModel: PlayingCardsViewModel by hiltNavGraphViewModels(R.id.main_graph)
    private val args: PlayingCardsGameFragmentArgs by navArgs()
    private val adapter = CardsAdapter()
    private val listForMemory = ArrayList<CardsUI>()
    private var defaultPositionInOneCard = 0
    private var countDownTimer: CountDownTimer? = null
    private var positionImageOne = 0
    private var positionImageTwo = 1
    private var positionImageThree = 2
    private var mSeconds: Long = 0
    private var correctSeconds = 0
    private var scoreSeconds: Long = 0

    override fun initialize() {
        setupTimerSplash()
        bindItemCards()
        setupPrefetching()
        binding.rvCards.adapter = adapter
    }

    private fun setupTimerSplash() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerSplash.txtTimerSplash.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.timerSplash.root.hide()
            }
        }
        timer.start()
    }

    private fun setupPrefetching() {
        val layoutManager = binding.rvCards.layoutManager as GridLayoutManager
        layoutManager.isItemPrefetchEnabled = true
        layoutManager.initialPrefetchItemCount = args.numbersOfCards
    }

    private fun bindItemCards() {
        if (args.numbersOfCards == 1) {
            binding.layoutItemCardsImageRight.hide()
            binding.layoutItemCardsImageLeft.hide()
        } else {
            binding.layoutItemCardsImageLeft.show()
            binding.layoutItemCardsImageRight.show()
        }
    }

    override fun setupViews() {
        correctSeconds = if (args.numbersOfCards == 1) {
            args.timeForMemoryCard
        } else {
            args.timeForMemoryCard * 3
        }
    }

    private fun setUpImageCards() = with(binding) {
        if (args.numbersOfCards == 1) {
            if (defaultPositionInOneCard <= listForMemory.lastIndex) {
                if (defaultPositionInOneCard > -1 || defaultPositionInOneCard >= listForMemory.lastIndex) {
                    defaultPositionInOneCard -= if (defaultPositionInOneCard > listForMemory.lastIndex) listForMemory.lastIndex else 0
                    itemCardsImageCenter.load(listForMemory[defaultPositionInOneCard].url.toString())
                } else {
                    binding.btnFinish.show()
                    setOnClickListenerBtnFinishGame()
                }
            }
        } else if (args.numbersOfCards == 3) {
            if (positionImageTwo < listForMemory.lastIndex && positionImageThree.toInt() != listForMemory.lastIndex && positionImageOne.toInt() <= listForMemory.lastIndex) {
                layoutItemCardsImageLeft.isVisible = true
                layoutItemCardsImageRight.isVisible = true
                itemCardsImageLeft.isVisible = true
                itemCardsImageRight.isVisible = true
                itemCardsImageLeft.load(listForMemory[positionImageOne].url.toString())
                itemCardsImageRight.load(listForMemory[positionImageThree].url.toString())
                itemCardsImageCenter.load(listForMemory[positionImageTwo].url.toString())
            } else if (positionImageTwo == listForMemory.lastIndex) {
                layoutItemCardsImageLeft.isVisible = false
                layoutItemCardsImageRight.isVisible = false
                itemCardsImageLeft.isVisible = false
                itemCardsImageRight.isVisible = false
                itemCardsImageCenter.load(listForMemory[positionImageOne].url.toString())
            } else {
                itemCardsImageCenter.load(listForMemory[listForMemory.lastIndex].url.toString())
            }
        }
    }

    override fun setupListeners() {
        setUpBtnBack()
    }

    private fun setUpTimer(
        arrayList: ArrayList<CardsUI>,
        uiState: UIState.Success<List<CardsUI>>,
        time: Int,
    ) {
        countDownTimer = timerInSeconds(
            binding.txtTimer,
            time.toLong(),
            1000,
            getMillsUntilFinished = { seconds ->
                mSeconds = seconds
            }, funOnFinish = {
                if (args.numbersOfCards == 1) {
                    defaultPositionInOneCard++
                    if (defaultPositionInOneCard <= listForMemory.lastIndex) {
                        arrayList[defaultPositionInOneCard.minus(1)] =
                            listForMemory[defaultPositionInOneCard.minus(1)]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(defaultPositionInOneCard.minus(1))
                        countDownTimer?.start()
                    } else {
                        arrayList[listForMemory.lastIndex] =
                            listForMemory[listForMemory.lastIndex]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(listForMemory.lastIndex)
                        binding.btnFinish.show()
                        btnNextAndBtnPreviousDisableClickable()
                        setOnClickListenerBtnFinishGame()
                    }
                } else if (args.numbersOfCards == 3) {
                    if (positionImageTwo <= listForMemory.lastIndex && positionImageThree <= listForMemory.lastIndex && positionImageOne <= listForMemory.lastIndex) {
                        arrayList[positionImageOne] =
                            listForMemory[positionImageOne]
                        arrayList[positionImageTwo] =
                            listForMemory[positionImageTwo]
                        arrayList[positionImageThree] =
                            listForMemory[positionImageThree]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(positionImageOne)
                        adapter.notifyItemChanged(positionImageTwo)
                        adapter.notifyItemChanged(positionImageThree)
                        positionImageOne += 3
                        positionImageTwo += 3
                        positionImageThree += 3
                        if (args.numbersOfCards == 3) {
                            setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                        } else {
                            setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                        }
                        setUpImageCards()
                        countDownTimer?.start()
                    } else {
                        binding.layoutItemCardsImageLeft.isVisible = false
                        binding.layoutItemCardsImageRight.isVisible = false
                        binding.itemCardsImageLeft.isVisible = false
                        binding.itemCardsImageRight.isVisible = false
                        Log.e("anime",
                            "POSITION ONE: $positionImageOne    POSITION TWO: $positionImageTwo   POSITION THREE: $positionImageThree")
                        if (positionImageTwo == listForMemory.lastIndex) {
                            arrayList[positionImageOne] =
                                listForMemory[positionImageOne]
                            adapter.submitList(arrayList)
                            adapter.notifyItemChanged(positionImageOne)
                            positionImageTwo++
                            setUpImageCards()
                            countDownTimer?.start()
                        } else {
                            arrayList[listForMemory.lastIndex] =
                                listForMemory[listForMemory.lastIndex]
                            adapter.submitList(arrayList)
                            adapter.notifyItemChanged(listForMemory.lastIndex)
                            binding.btnPrevious.disableClickable()
                            binding.btnNext.disableClickable()
                            binding.btnFinish.show()
                            cancelCountDownTimer()
                            setOnClickListenerBtnFinishGame()
                        }
                    }
                }
            })
        countDownTimer?.start()
    }


    private fun setUpBtnBack() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateSafely(
                PlayingCardsGameFragmentDirections
                    .actionPlayingCardsGameFragmentToExitDialogFragment(
                        false
                    ))
        }
        overrideOnBackPressed {
            findNavController().navigateSafely(PlayingCardsGameFragmentDirections
                .actionPlayingCardsGameFragmentToExitDialogFragment(
                    false
                ))
        }
    }

    override fun setupRequests() {
        viewModel.fetchCards(
            typeClover = args.isClover,
            typeBrick = args.isbrick,
            typePiqui = args.ispiqui,
            typeRedHeard = args.isredHeart
        )
    }

    override fun setupObserves() = with(binding) {
        viewModel.fetchCardsState.subscribe { uiState ->
            loaderCards.isVisible = uiState is UIState.Loading
            when (uiState) {
                is UIState.Error -> {
                    Log.e("anime", "Error Cards: ${uiState.error}")
                }
                is UIState.Loading -> {
                    Log.e("anime", "Loading Cards: $uiState")
                }
                is UIState.Success -> {
                    Log.e("anime", "Fetch Cards ${uiState.data}")
                    val arrayList = ArrayList<CardsUI>()
                    listForMemory.addAll(uiState.data)
                    uiState.data.forEach {
                        arrayList.add(CardsUI("", id = it.id, ""))
                        adapter.submitList(arrayList)
                    }
                    if (args.numbersOfCards == 3) {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                    } else {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                    }
                    bindSetOnClickListenerBtnNextAndBtnPrevious(arrayList, uiState)
                    setUpImageCards()
                }
            }
        }
    }

    private fun bindSetOnClickListenerBtnNextAndBtnPrevious(
        arrayList: ArrayList<CardsUI>,
        uiState: UIState.Success<List<CardsUI>>,
    ) {
        setUpClickListenerBtnNext(arrayList, uiState)
        setUpClickListenerBtnPrevious(arrayList, uiState)
    }

    private fun setUpClickListenerBtnNext(
        arrayList: ArrayList<CardsUI>,
        uiState: UIState.Success<List<CardsUI>>,
    ) {
        binding.btnNext.setOnSingleClickListener {
            if (args.numbersOfCards == 1) {
                defaultPositionInOneCard++
                if (defaultPositionInOneCard <= listForMemory.lastIndex) {
                    Log.e("anime",
                        "LAST INDEX ${listForMemory.lastIndex} LIST SIZE ${listForMemory.size} ADAPTER ITEM COUNT ${adapter.itemCount}")
                    arrayList[defaultPositionInOneCard.minus(1)] =
                        listForMemory[defaultPositionInOneCard.minus(1)]
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(defaultPositionInOneCard.minus(1))
                    scoreSeconds += (correctSeconds - mSeconds)
                    countDownTimer?.cancel()
                    countDownTimer = null
                    if (args.numbersOfCards == 3) {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                    } else {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                    }
                    setUpImageCards()
                } else {
                    scoreSeconds += (correctSeconds - mSeconds)
                    arrayList[listForMemory.lastIndex] =
                        listForMemory[listForMemory.lastIndex]
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(listForMemory.lastIndex)
                    cancelCountDownTimer()
                    binding.btnPrevious.disableClickable()
                    binding.btnNext.disableClickable()
                    binding.itemCardsImageCenter.load(getString(R.string.ic_card_back))
                    binding.btnFinish.show()
                    setOnClickListenerBtnFinishGame()
                }
            } else if (args.numbersOfCards == 3) {
                if (positionImageTwo <= listForMemory.lastIndex && positionImageThree <= listForMemory.lastIndex && positionImageOne <= listForMemory.lastIndex) {
                    arrayList[positionImageOne] =
                        listForMemory[positionImageOne]
                    arrayList[positionImageTwo] =
                        listForMemory[positionImageTwo]
                    arrayList[positionImageThree] =
                        listForMemory[positionImageThree]
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(positionImageOne)
                    adapter.notifyItemChanged(positionImageTwo)
                    adapter.notifyItemChanged(positionImageThree)
                    positionImageOne += 3
                    positionImageTwo += 3
                    positionImageThree += 3
                    scoreSeconds += (correctSeconds - mSeconds)
                    cancelCountDownTimer()
                    if (args.numbersOfCards == 3) {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                    } else {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                    }
                    setUpImageCards()
                } else {
                    Log.e("anime",
                        "POSITION ONE: $positionImageOne    POSITION TWO: $positionImageTwo   POSITION THREE: $positionImageThree")
                    binding.layoutItemCardsImageLeft.isVisible = false
                    binding.layoutItemCardsImageRight.isVisible = false
                    binding.itemCardsImageLeft.isVisible = false
                    binding.itemCardsImageRight.isVisible = false
                    if (positionImageTwo == listForMemory.lastIndex) {
                        arrayList[positionImageOne] =
                            listForMemory[positionImageOne]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(positionImageOne)
                        positionImageTwo++
                        setUpImageCards()
                    } else {
                        arrayList[listForMemory.lastIndex] =
                            listForMemory[listForMemory.lastIndex]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(listForMemory.lastIndex)
                        binding.btnPrevious.disableClickable()
                        binding.btnNext.disableClickable()
                        binding.btnFinish.show()
                        cancelCountDownTimer()
                        setOnClickListenerBtnFinishGame()
                    }
                }
            }
        }
    }

    private fun setUpClickListenerBtnPrevious(
        arrayList: ArrayList<CardsUI>,
        uiState: UIState.Success<List<CardsUI>>,
    ) {
        binding.btnPrevious.setOnSingleClickListener {
            if (args.numbersOfCards == 1) {
                if (defaultPositionInOneCard >= -1) {
                    defaultPositionInOneCard -= if (defaultPositionInOneCard > 0) 1 else 0
                    arrayList[defaultPositionInOneCard] =
                        CardsUI("", 2, "")
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(defaultPositionInOneCard)
                    cancelCountDownTimer()
                    if (args.numbersOfCards == 3) {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                    } else {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                    }
                    binding.btnPrevious.isClickable = true
                } else {
                    binding.btnPrevious.isClickable = false
                    Log.e("anime", "Finish")
                }
            } else {
                if (positionImageOne != listForMemory.size && positionImageThree != listForMemory.lastIndex && positionImageTwo <= listForMemory.lastIndex) {
                    positionImageOne -= if (positionImageOne > 0) 3 else 0
                    positionImageTwo -= if (positionImageTwo > 1) 3 else 0
                    positionImageThree -= if (positionImageThree > 2) 3 else 0
                    Log.e("anime",
                        "LAST INDEX ${listForMemory.lastIndex} LIST SIZE ${listForMemory.size} ADAPTER ITEM COUNT ${adapter.itemCount}")
                    arrayList[positionImageOne] =
                        CardsUI("", 3, "")
                    arrayList[positionImageTwo] =
                        CardsUI("", 2, "")
                    arrayList[positionImageThree] =
                        CardsUI("", 1, "")
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(positionImageOne)
                    adapter.notifyItemChanged(positionImageTwo)
                    adapter.notifyItemChanged(positionImageThree)
                    cancelCountDownTimer()
                    if (args.numbersOfCards == 3) {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                    } else {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                    }
                    binding.btnPrevious.isClickable = true
                } else {
                    binding.btnPrevious.isClickable = false
                }
            }
            setUpImageCards()
        }
    }


    override fun onStop() {
        super.onStop()
        cancelCountDownTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelCountDownTimer()
    }

    private fun cancelCountDownTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun btnNextAndBtnPreviousDisableClickable() {
        binding.btnNext.disableClickable()
        binding.btnPrevious.disableClickable()
    }

    private fun setOnClickListenerBtnFinishGame() {
        binding.btnFinish.setOnSingleClickListener {
            findNavController().navigateSafely(
                PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                    numbersOfCards = args.numbersOfCards,
                    isClover = args.isClover,
                    ispiqui = args.ispiqui,
                    isredHeart = args.isredHeart,
                    isbrick = args.isbrick,
                    time = args.time,
                    memoryList = listForMemory.toTypedArray(),
                    memorizationTimeOfAllCards = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        (scoreSeconds % 3600) / 60,
                        scoreSeconds % 60
                    )
                )
            )
        }
    }

}




