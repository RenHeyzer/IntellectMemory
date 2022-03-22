package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.answer

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentAnswerRandomNumbersBinding
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.presentation.adapters.AnswerRandomNumbersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnswerRandomNumbersFragment :
    BaseFragment<FragmentAnswerRandomNumbersBinding, AnswerRandomNumbersViewModel>(
        R.layout.fragment_answer_random_numbers
    ) {
    override val binding by viewBinding(FragmentAnswerRandomNumbersBinding::bind)
    override val viewModel: AnswerRandomNumbersViewModel by viewModels()
    private val args: AnswerRandomNumbersFragmentArgs by navArgs()
    private var adapter: AnswerRandomNumbersAdapter? = null
    private val answerList: ArrayList<AnswerNumbersModel> = ArrayList()
    private val identifierList: ArrayList<Int> = ArrayList()
    private var lastPosition: Int = 6
    private var rowLastPosition: Int = 0

    override fun initialize() {
        adapter = AnswerRandomNumbersAdapter(this::onInput)
        binding.apply {
            rvGameRandomNumber.adapter = adapter
            txtTimer.start()
        }
    }

    override fun setupListeners() {
        setUpBtnNextAndBtnPreviousListener()
    }

    override fun setupRequests() {
        viewModel.getAllRandomNumbers()
    }

    override fun setupObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomNumbersState.collect {
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
        }
    }

    private fun setUpBtnNextAndBtnPreviousListener() {
        adapter?.setNextAndPrevious(lastPosition, rowLastPosition, args.size)
        binding.btnNext.setOnSingleClickListener {
            lastPosition += 7
            rowLastPosition += 7
            adapter?.setNextAndPrevious(lastPosition, rowLastPosition, args.size)
            Log.e("promo", "Plus: $lastPosition")
        }
        binding.btnPrevious.setOnSingleClickListener {
            if (lastPosition >= 6 || rowLastPosition >= 0) {
                lastPosition -= 7
                rowLastPosition -= 7
                adapter?.setNextAndPrevious(lastPosition, rowLastPosition, args.size)
                Log.e("promo", "Minus: $lastPosition")
                Log.e("promo", "Minus: $rowLastPosition")
            } else {
                lastPosition = 6
                rowLastPosition = 0
            }
        }
    }

    private fun onInput(position: Int, number: Int?) {
        answerList[position].answerNumber = number
        Log.e("perfect", "${identifierList[position]} \n $number")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}