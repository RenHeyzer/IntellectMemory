package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.result

import android.util.Log
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.doubleList
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.common.extension.overrideOnBackPressed
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentResultCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.ui.adapters.CardsResultAdapter
import com.geektech.intellect_memort.presentation.ui.fragments.playingcards.PlayingCardsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsResultFragment : BaseFragment<FragmentResultCardsBinding, PlayingCardsViewModel>(
    R.layout.fragment_result_cards
) {
    override val binding by viewBinding(FragmentResultCardsBinding::bind)
    override val viewModel: PlayingCardsViewModel by hiltNavGraphViewModels(R.id.main_graph)
    private val args by navArgs<CardsResultFragmentArgs>()
    private val memoryList = ArrayList<CardsUI>()
    private val answerList = ArrayList<CardsUI>()
    private val checkList = ArrayList<Boolean>()
    private val adapter = CardsResultAdapter(checkList)
    private var score = 0

    override fun initialize() {
        setupPrefetching()
        binding.rvCardsResult.adapter = adapter
    }

    private fun setupPrefetching() {
        val layoutManager = binding.rvCardsResult.layoutManager as GridLayoutManager
        layoutManager.isItemPrefetchEnabled = true
        layoutManager.initialPrefetchItemCount = args.numbersOfCards
    }

    override fun setupViews() = with(binding) {
        timeToRemember.text = args.memorizationTimeOfAllCards
        timeOfAnswers.text = args.time

        memoryList.addAll(args.memoryList)
        answerList.addAll(args.anwerList)

        memoryList.doubleList(answerList) {
            if (it.second.id == it.first.id && it.second.type == it.first.type && it.second.id != null) {
                checkList.add(true)
                score += 1
            } else {
                checkList.add(false)
            }
        }
        viewModel.passResults(score)
        binding.scoreNumbers.text = score.toString()
    }

    override fun setupListeners() {
        setupBack()
        setupFinish()
    }

    private fun setupFinish() {
        binding.btnFinish.setOnSingleClickListener {
            findNavController().navigateSafely(
                CardsResultFragmentDirections.actionCardsResultFragmentToHomeFragment()
            )
        }
    }

    private fun setupBack() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateSafely(
                CardsResultFragmentDirections.actionCardsResultFragmentToHomeFragment()
            )
        }

        overrideOnBackPressed {
            findNavController().navigateSafely(
                CardsResultFragmentDirections.actionCardsResultFragmentToHomeFragment()
            )
        }
    }

    override fun setupRequests() {
        Log.e("datames", viewModel.showAnswerCardsState.value.toString())
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showAnswerCardsState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}