package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.answer

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.correctNumber
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.common.extension.overrideOnBackPressed
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentAnswerNumbersBinding
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.presentation.ui.adapters.AnswerNumbersAdapter
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

    override fun setupViews() {
        binding.btnPrevious.isVisible = false
        binding.btnNext.isVisible = false
        binding.btnEndFinish.isVisible = false
        binding.btnEndNext.isVisible = true
    }

    override fun setupListeners() {
        setupBackClickListener()
        setUpBtnNextAndBtnPreviousListener()
        setupFinishClickListener()
    }

    private fun setupBackClickListener() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateSafely(
                AnswerNumbersFragmentDirections.actionAnswerNumbersFragmentToExitDialogFragment(true)
            )
        }
        overrideOnBackPressed {
            findNavController().navigateSafely(
                AnswerNumbersFragmentDirections.actionAnswerNumbersFragmentToExitDialogFragment(true)
            )
        }
    }

    private fun setupFinishClickListener() {
        binding.btnResult.setOnSingleClickListener {
            viewModel.insertAllAnswerOfNumbers(answerList)
            findNavController().navigateSafely(
                AnswerNumbersFragmentDirections.actionAnswerNumbersFragmentToResultNumbersFragment(
                    args.timeToRemember,
                    binding.txtTimer.text.toString()
                )
            )
        }
        binding.btnEndFinish.setOnSingleClickListener {
            viewModel.insertAllAnswerOfNumbers(answerList)
            findNavController().navigateSafely(
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
        val correctQuantity = args.size.correctNumber()
        viewModel.randomNumbersState.subscribeIdle {
            it.forEach { model ->
                model.id?.let { id ->
                    if (answerList.size <= correctQuantity) {
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
    private fun setUpBtnNextAndBtnPreviousListener() = with(binding) {
        adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
        btnNext.setOnSingleClickListener {
            lastPosition += 7
            rowLastPosition += 7
            setupButtonsPrevAndNextVisibility()
            adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
            adapter?.notifyDataSetChanged()
        }
        btnPrevious.setOnSingleClickListener {
            if (lastPosition >= 6 || rowLastPosition >= 0) {
                lastPosition -= 7
                rowLastPosition -= 7
                setupButtonsPrevAndNextVisibility()
                adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
                adapter?.notifyDataSetChanged()
            } else {
                lastPosition = 6
                rowLastPosition = 0
            }
        }
        btnEndNext.setOnSingleClickListener {
            lastPosition += 7
            rowLastPosition += 7
            setupButtonsPrevAndNextVisibility()
            adapter?.setNextAndPrevious(lastPosition, rowLastPosition)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setupButtonsPrevAndNextVisibility() = with(binding) {
        adapter?.itemCount?.let {
            when {
                lastPosition != 6 && lastPosition < it -> {
                    Log.e("lasteed", "1")
                    btnPrevious.isVisible = true
                    btnNext.isVisible = true
                    btnEndNext.isVisible = false
                    btnEndFinish.isVisible = false
                }
                lastPosition != 6 && lastPosition > it -> {
                    btnPrevious.isVisible = false
                    btnNext.isVisible = false
                    Log.e("lasteed", "2")
                    btnEndNext.isVisible = false
                    btnEndFinish.isVisible = true
                }
                lastPosition == 6 && lastPosition < it -> {
                    Log.e("lasteed", "else")
                    btnPrevious.isVisible = false
                    btnNext.isVisible = false
                    btnEndNext.isVisible = true
                    btnEndFinish.isVisible = false
                }
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