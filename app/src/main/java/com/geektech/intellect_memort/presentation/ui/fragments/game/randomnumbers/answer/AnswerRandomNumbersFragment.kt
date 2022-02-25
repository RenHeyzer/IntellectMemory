package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.answer

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentAnswerRandomNumbersBinding
import com.geektech.intellect_memort.presentation.adapters.AnswerRandomNumbersAdapter
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import com.geektech.intellect_memort.presentation.state.UIState
import com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers.GameRandomNumbersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnswerRandomNumbersFragment :
    BaseFragment<FragmentAnswerRandomNumbersBinding, GameRandomNumbersViewModel>(
        R.layout.fragment_answer_random_numbers
    ) {
    override val binding by viewBinding(FragmentAnswerRandomNumbersBinding::bind)
    override val viewModel: GameRandomNumbersViewModel by activityViewModels()
    private var adapter: AnswerRandomNumbersAdapter? = null
    private var list = ArrayList<RandomNumbersModel>()
    private var row: Int = 7
    private var last: Int = 14

    override fun initialize() {
        adapter = AnswerRandomNumbersAdapter(list)
        binding.rvGameRandomNumber.adapter = adapter
        binding.txtTimer.start()
    }

    override fun setupListeners() {
        setUpBtnNextAndBtnPreviousListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpBtnNextAndBtnPreviousListener() {
        binding.btnNext.setOnSingleClickListener {
            row += 7
            last += 7
            adapter?.setNextAndPreviousItemRow(row, last)
            adapter?.notifyDataSetChanged()
            Log.e("anime", "Plus: $row")
            Log.e("anime", "Plus: $list")
        }
        binding.btnPrevious.setOnSingleClickListener {
            row -= 7
            last -= 7
            adapter?.setNextAndPreviousItemRow(row, last)
            Log.e("anime", "Minus: $row")
            adapter?.notifyDataSetChanged()
        }
    }

    override fun setupObserves() {
            viewModel.randomNumbersState.subscribe {
                when (it) {
                    is UIState.Error -> {
                        Log.e("anime", "Error RandomNumbers:${it.error} ")
                    }
                    is UIState.Loading -> {
                        Log.e("anime", "Loading RandomNumbers $it")

                    }
                    is UIState.Success -> {
                        Log.e("anime", "Success RandomNumbers:${it.data} ")
                        adapter?.submitList(it.data)
                    }
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}