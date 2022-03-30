package com.geektech.intellect_memort.presentation.ui.fragments.game

import android.util.Log
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentResultNumbersBinding
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import com.geektech.intellect_memort.presentation.adapters.ResultNumbersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultNumbersFragment : BaseFragment<FragmentResultNumbersBinding, ResultNumbersViewModel>(
    R.layout.fragment_result_numbers
) {
    override val binding by viewBinding(FragmentResultNumbersBinding::bind)
    override val viewModel by viewModels<ResultNumbersViewModel>()
    private var adapter: ResultNumbersAdapter? = null
    private val numbersList: ArrayList<RandomNumbersModel> = ArrayList()
    private val checkList: ArrayList<Boolean> = ArrayList()
    private var score: Int = 0

    override fun initialize() {
        super.initialize()
        adapter = ResultNumbersAdapter(checkList)
        binding.rvResultNumbers.adapter = adapter
    }

    override fun setupObserves() {
        viewModel.randomNumbersState.subscribeIdle {
            numbersList.addAll(it)
        }

        viewModel.answerNumbersState.subscribeIdle {
            val result = numbersList.asSequence().zip(it.asSequence())
            result.asSequence().forEach { data ->
                Log.e(
                    "hero",
                    "answer " + data.second.answerNumber.toString() + "==" + "number " + data.first.number.toString()
                )
                if (data.second.answerNumber == data.first.number && data.second.answerNumber != null) {
                    checkList.add(true)
                    score += 1
                } else {
                    checkList.add(false)
                }
            }
            binding.scoreNumbers.text = score.toString()
            adapter?.submitList(it)
        }
    }
}