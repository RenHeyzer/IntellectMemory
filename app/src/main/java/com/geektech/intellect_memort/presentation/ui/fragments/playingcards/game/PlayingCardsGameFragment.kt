package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.game

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.*
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
    private val listForMemory = ArrayList<CardsUI>()
    private var defaultPositionInOneCard = 0
    private var countDownTimer: CountDownTimer? = null
    private var positionForCards = 0
    override fun initialize() {
        bindItemCards()
        binding.rvCards.adapter = adapter
    }

    private fun bindItemCards() {
        if (args.numbersOfCards == 1) {
            binding.layoutItemCardsImageRight.visibility = View.GONE
            binding.layoutItemCardsImageLeft.visibility = View.GONE
        } else {
            binding.layoutItemCardsImageLeft.visibility = View.VISIBLE
            binding.layoutItemCardsImageRight.visibility = View.VISIBLE
        }
    }

    private fun setUpImageCards() = with(binding) {
        if (args.numbersOfCards == 1) {
            if (defaultPositionInOneCard != listForMemory.size && defaultPositionInOneCard != listForMemory.lastIndex) {
                if (defaultPositionInOneCard > -1) {
                    itemCardsImageCenter.loadUrlWithCoil(listForMemory[defaultPositionInOneCard].url.toString())
                } else {
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.btnFinish.setOnSingleClickListener {
                        findNavController().navigateSafely(
                            PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                numbersOfCards = args.numbersOfCards,
                                isClover = args.isClover,
                                ispiqui = args.ispiqui,
                                isredHeart = args.isredHeart,
                                isbrick = args.isbrick,
                                timeForMemoryCard = args.timeForMemoryCard,
                                time = args.time
                            )
                        )
                    }
                }
            }
        } else {
            if (positionForCards.plus(2) != listForMemory.size && positionForCards != listForMemory.lastIndex) {
                layoutItemCardsImageLeft.isVisible = true
                layoutItemCardsImageRight.isVisible = true
                itemCardsImageLeft.isVisible = true
                itemCardsImageRight.isVisible = true
                itemCardsImageLeft.loadUrlWithCoil(listForMemory[positionForCards].url.toString())
                itemCardsImageRight.loadUrlWithCoil(listForMemory[positionForCards.plus(1)].url.toString())
                itemCardsImageCenter.loadUrlWithCoil(listForMemory[positionForCards.plus(2)].url.toString())
            } else {
                layoutItemCardsImageLeft.isVisible = false
                layoutItemCardsImageRight.isVisible = false
                itemCardsImageLeft.isVisible = false
                itemCardsImageRight.isVisible = false
                itemCardsImageCenter.loadUrlWithCoil(listForMemory[listForMemory.lastIndex].url.toString())
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
            1000
        ) {
            if (args.numbersOfCards == 1) {
                defaultPositionInOneCard++
                if (defaultPositionInOneCard != uiState.data.size) {
                    setUpImageCards()
                    arrayList[defaultPositionInOneCard.minus(1)] =
                        uiState.data[defaultPositionInOneCard.minus(1)]
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(defaultPositionInOneCard.minus(1))
                    countDownTimer?.start()
                } else {
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.btnFinish.setOnSingleClickListener {
                        findNavController().navigateSafely(
                            PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                numbersOfCards = args.numbersOfCards,
                                isClover = args.isClover,
                                ispiqui = args.ispiqui,
                                isredHeart = args.isredHeart,
                                isbrick = args.isbrick,
                                timeForMemoryCard = args.timeForMemoryCard,
                                time = args.time
                            )
                        )
                    }
                }
            } else if (args.numbersOfCards == 3) {
                if (positionForCards.minus(1) != uiState.data.lastIndex) {
                    if (positionForCards.plus(2) != listForMemory.size && positionForCards != listForMemory.lastIndex) {
                        positionForCards += 3
                        setUpImageCards()
                        arrayList[positionForCards.minus(1)] =
                            uiState.data[positionForCards.minus(1)]
                        arrayList[positionForCards.minus(2)] =
                            uiState.data[positionForCards.minus(2)]
                        arrayList[positionForCards.minus(3)] =
                            uiState.data[positionForCards.minus(3)]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(positionForCards.minus(1))
                        adapter.notifyItemChanged(positionForCards.minus(2))
                        adapter.notifyItemChanged(positionForCards.minus(3))
                        countDownTimer?.start()
                    } else {
                        arrayList[listForMemory.lastIndex] =
                            uiState.data[listForMemory.lastIndex]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(listForMemory.lastIndex)
                        binding.btnFinish.visibility = View.VISIBLE
                        binding.btnFinish.setOnSingleClickListener {
                            findNavController().navigateSafely(
                                PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                    numbersOfCards = args.numbersOfCards,
                                    isClover = args.isClover,
                                    ispiqui = args.ispiqui,
                                    isredHeart = args.isredHeart,
                                    isbrick = args.isbrick,
                                    timeForMemoryCard = args.timeForMemoryCard,
                                    time = args.time
                                )
                            )
                        }
                    }
                } else {
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.btnFinish.setOnSingleClickListener {
                        findNavController().navigateSafely(
                            PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                numbersOfCards = args.numbersOfCards,
                                isClover = args.isClover,
                                ispiqui = args.ispiqui,
                                isredHeart = args.isredHeart,
                                isbrick = args.isbrick,
                                timeForMemoryCard = args.timeForMemoryCard,
                                time = args.time
                            )
                        )
                    }
                }
            }
        }
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
        viewModel.fetchImageOfCards(
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
                if (defaultPositionInOneCard != uiState.data.size) {
                    arrayList[defaultPositionInOneCard.minus(1)] =
                        uiState.data[defaultPositionInOneCard.minus(1)]
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(defaultPositionInOneCard.minus(1))
                    countDownTimer?.cancel()
                    countDownTimer = null
                    if (args.numbersOfCards == 3) {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                    } else {
                        setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                    }
                } else {
                    countDownTimer?.cancel()
                    countDownTimer = null
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.btnFinish.setOnSingleClickListener {
                        findNavController().navigateSafely(
                            PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                numbersOfCards = args.numbersOfCards,
                                isClover = args.isClover,
                                ispiqui = args.ispiqui,
                                isredHeart = args.isredHeart,
                                isbrick = args.isbrick,
                                timeForMemoryCard = args.timeForMemoryCard,
                                time = args.time
                            )
                        )
                    }
                }
            } else if (args.numbersOfCards == 3) {
                if (positionForCards.minus(1) <= uiState.data.lastIndex) {
                    if (positionForCards.plus(2) != listForMemory.size && positionForCards != listForMemory.lastIndex) {
                        Log.e("anime",
                            "LAST INDEX ${listForMemory.lastIndex} LIST SIZE ${listForMemory.size} ADAPTER ITEM COUNT ${adapter.itemCount}")
                        positionForCards += 3
                        arrayList[positionForCards.minus(1)] =
                            uiState.data[positionForCards.minus(1)]
                        arrayList[positionForCards.minus(2)] =
                            uiState.data[positionForCards.minus(2)]
                        arrayList[positionForCards.minus(3)] =
                            uiState.data[positionForCards.minus(3)]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(positionForCards.minus(1))
                        adapter.notifyItemChanged(positionForCards.minus(2))
                        adapter.notifyItemChanged(positionForCards.minus(3))
                        countDownTimer = null
                        countDownTimer?.cancel()
                        if (args.numbersOfCards == 3) {
                            setUpTimer(arrayList, uiState, args.timeForMemoryCard * 3)
                        } else {
                            setUpTimer(arrayList, uiState, args.timeForMemoryCard)
                        }

                    } else {
                        arrayList[listForMemory.lastIndex] =
                            uiState.data[listForMemory.lastIndex]
                        adapter.submitList(arrayList)
                        adapter.notifyItemChanged(listForMemory.lastIndex)
                        binding.btnFinish.visibility = View.VISIBLE
                        binding.btnFinish.setOnSingleClickListener {
                            findNavController().navigateSafely(
                                PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                    numbersOfCards = args.numbersOfCards,
                                    isClover = args.isClover,
                                    ispiqui = args.ispiqui,
                                    isredHeart = args.isredHeart,
                                    isbrick = args.isbrick,
                                    timeForMemoryCard = args.timeForMemoryCard,
                                    time = args.time
                                )
                            )
                        }
                    }
                } else {
                    binding.btnFinish.visibility = View.VISIBLE
                    binding.btnFinish.setOnSingleClickListener {
                        findNavController().navigateSafely(
                            PlayingCardsGameFragmentDirections.actionPlayingCardsGameFragmentToAnswerPlayingCardsFragment(
                                numbersOfCards = args.numbersOfCards,
                                isClover = args.isClover,
                                ispiqui = args.ispiqui,
                                isredHeart = args.isredHeart,
                                isbrick = args.isbrick,
                                timeForMemoryCard = args.timeForMemoryCard,
                                time = args.time
                            )
                        )
                    }
                }
            }
            setUpImageCards()
        }
    }

    private fun setUpClickListenerBtnPrevious(
        arrayList: ArrayList<CardsUI>,
        uiState: UIState.Success<List<CardsUI>>,
    ) {
        binding.btnPrevious.setOnSingleClickListener {
            if (args.numbersOfCards == 1) {
                if (defaultPositionInOneCard > -1) {
                    defaultPositionInOneCard--
                    arrayList[defaultPositionInOneCard] =
                        CardsUI("", 2, "")
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(defaultPositionInOneCard)
                    countDownTimer?.cancel()
                    countDownTimer = null
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
                if (positionForCards.plus(2) != listForMemory.size && positionForCards != listForMemory.lastIndex) {
                    positionForCards -= if (positionForCards > 0) -3 else 0
                    Log.e("anime",
                        "LAST INDEX ${listForMemory.lastIndex} LIST SIZE ${listForMemory.size} ADAPTER ITEM COUNT ${adapter.itemCount}")
                    arrayList[positionForCards.minus(1)] =
                        CardsUI("", 3, "")
                    arrayList[positionForCards.minus(2)] =
                        CardsUI("", 2, "")
                    arrayList[positionForCards.minus(3)] =
                        CardsUI("", 1, "")
                    adapter.submitList(arrayList)
                    adapter.notifyItemChanged(positionForCards.minus(1))
                    adapter.notifyItemChanged(positionForCards.minus(2))
                    adapter.notifyItemChanged(positionForCards.minus(3))
                    countDownTimer?.cancel()
                    countDownTimer = null
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
        countDownTimer?.cancel()
        countDownTimer = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
    }

}




