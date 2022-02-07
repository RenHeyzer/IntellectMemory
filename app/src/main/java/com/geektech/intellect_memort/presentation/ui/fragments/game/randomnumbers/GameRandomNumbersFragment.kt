package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.gone
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentGameRandomNumbersBinding
import com.geektech.intellect_memort.presentation.adapters.RandomNumbersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameRandomNumbersFragment :
    BaseFragment<FragmentGameRandomNumbersBinding, GameRandomNumbersViewModel>(
        R.layout.fragment_game_random_numbers
    ) {
    override val binding by viewBinding(FragmentGameRandomNumbersBinding::bind)
    override val viewModel: GameRandomNumbersViewModel by viewModels()
    private val args: GameRandomNumbersFragmentArgs by navArgs()
    private var row: Int = 7
    private var last: Int = 14
    private var adapter: RandomNumbersAdapter? = null

    override fun initialize() {
        adapter = RandomNumbersAdapter()
        binding.rvGameRandomNumber.adapter = adapter
        viewModel.createRandomNumbers(args.quantitynumber)
    }

    override fun setupListeners() {
        setUpBtnBack()
        setUpBtnNextAndBtnPreviousListener()
        setUpBtnFinish()

    }

    private fun setUpBtnFinish() {
        binding.btnFinish.setOnSingleClickListener {
//            if (viewModel.list.lastOrNull { it.numbers < 0 } == null)
        }
    }

    override fun setupObserves() {
        viewModel.randomNumbersState.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpBtnNextAndBtnPreviousListener() {
        binding.btnNext.setOnSingleClickListener {
            if (viewModel.list.lastIndex != args.quantitynumber) {
                row += 7
                last += 7
                adapter?.setNextAndPreviousItemRow(row, last)
                adapter?.notifyDataSetChanged()
                Log.e("anime", "Plus: $row")
            } else {
                binding.btnFinish.gone()
            }
        }
        binding.btnPrevious.setOnSingleClickListener {
            if (row >= 7 && last >= 14) {
                row -= 7
                last -= 7
                adapter?.setNextAndPreviousItemRow(row, last)
                Log.e("anime", "Minus: $row")
                adapter?.notifyDataSetChanged()
            } else {
                row = 7
                last = 14
            }
        }
    }

    private fun setUpBtnBack() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigate(R.id.action_gameRandomNumbersFragment_to_homeFragment)
        }

    }

    @SuppressLint("RestrictedApi")
    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}