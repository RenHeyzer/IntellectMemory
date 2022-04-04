package com.geektech.intellect_memort.presentation.ui.fragments.game

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.doubleList
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentResultNumbersBinding
import com.geektech.intellect_memort.domain.models.NumbersModel
import com.geektech.intellect_memort.presentation.adapters.ResultNumbersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultNumbersFragment : BaseFragment<FragmentResultNumbersBinding, ResultNumbersViewModel>(
    R.layout.fragment_result_numbers
) {
    override val binding by viewBinding(FragmentResultNumbersBinding::bind)
    override val viewModel by viewModels<ResultNumbersViewModel>()
    private val args by navArgs<ResultNumbersFragmentArgs>()
    private var adapter: ResultNumbersAdapter? = null
    private val numbersList: ArrayList<NumbersModel> = ArrayList()
    private val checkList: ArrayList<Boolean> = ArrayList()
    private var score: Int = 0

    override fun initialize() {
        super.initialize()
        adapter = ResultNumbersAdapter(checkList)
        binding.rvResultNumbers.adapter = adapter
    }

    override fun setupViews() = with(binding) {
        timeToRemember.text = args.timeToRemember
        timeOfAnswers.text = args.timeToAnswer
    }

    override fun setupListeners() {
        setupButtonFinish()
    }

    private fun setupButtonFinish() {
        binding.btnFinish.setOnSingleClickListener {
            findNavController().navigate(
                ResultNumbersFragmentDirections.actionResultNumbersFragmentToExitDialogFragment(
                    false)
            )
        }
    }

    override fun setupObserves() {
        viewModel.randomNumbersState.subscribeIdle {
            numbersList.addAll(it)
        }

        viewModel.answerNumbersState.subscribeIdle {
            numbersList.doubleList(it) { data ->
                if (data.second.answerNumber == data.first.number && data.second.answerNumber != null) {
                    checkList.add(true)
                    score += 1
                } else {
                    checkList.add(false)
                }
            }
            binding.scoreNumbers.text = score.toString()
            adapter?.submitList(it)
            viewModel.passResults(score)
        }
    }
}