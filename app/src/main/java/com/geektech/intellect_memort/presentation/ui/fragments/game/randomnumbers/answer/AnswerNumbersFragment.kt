package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.answer

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentAnswerNumbersBinding
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.presentation.adapters.AnswerNumbersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerNumbersFragment :
    BaseFragment<FragmentAnswerNumbersBinding, AnswerNumbersViewModel>(
        R.layout.fragment_answer_numbers
    ) {
    override val binding by viewBinding(FragmentAnswerNumbersBinding::bind)
    override val viewModel: AnswerNumbersViewModel by viewModels()
    private val args: AnswerNumbersFragmentArgs by navArgs()
    private var adapter: AnswerNumbersAdapter? = null
    private val answerList: ArrayList<AnswerNumbersModel> = ArrayList()
    private val identifierList: ArrayList<Int> = ArrayList()
    private var lastPosition: Int = 6
    private var rowLastPosition: Int = 0

    override fun initialize() {
        adapter = AnswerNumbersAdapter(this::onInput)
        binding.apply {
            rvGameRandomNumber.adapter = adapter
            txtTimer.start()
        }
    }

    override fun setupListeners() {
        setupBackClickListener()
        setUpBtnNextAndBtnPreviousListener()
        setupFinishClickListener()
    }

    private fun setupBackClickListener() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigate(
                AnswerNumbersFragmentDirections.actionAnswerNumbersFragmentToExitDialogFragment(true)
            )
        }
    }

    private fun setupFinishClickListener() {
        binding.btnResult.setOnSingleClickListener {
            viewModel.insertAllAnswerOfNumbers(answerList)
            findNavController().navigate(
                AnswerNumbersFragmentDirections.actionAnswerNumbersFragmentToResultNumbersFragment(
                    args.timeToRemember,
                    binding.txtTimer.text.toString()
                )
            )
        }
    }

    override fun setupRequests() {
        viewModel.getAllRandomNumbers()
    }

    override fun setupObserves() {
        viewModel.randomNumbersState.subscribeIdle {
            it.forEach { model ->
                model.id?.let { id ->
                    if (answerList.size <= args.size) {
                        answerList.add(AnswerNumbersModel(id, null))
                        identifierList.add(id)
                    }
                }
            }
            if (answerList.isNotEmpty()) {
                adapter?.submitList(answerList)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpBtnNextAndBtnPreviousListener() {
        adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
        binding.btnNext.setOnSingleClickListener {
            lastPosition += 7
            rowLastPosition += 7
            adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
            adapter?.notifyDataSetChanged()
        }
        binding.btnPrevious.setOnSingleClickListener {
            if (lastPosition >= 6 || rowLastPosition >= 0) {
                lastPosition -= 7
                rowLastPosition -= 7
                adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
                adapter?.notifyDataSetChanged()
            } else {
                lastPosition = 6
                rowLastPosition = 0
            }
        }
    }

    private fun onInput(position: Int, number: Int?) {
        answerList[position].answerNumber = number
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}