package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentGameRandomNumbersBinding
import com.geektech.intellect_memort.presentation.adapters.RandomNumbersAdapter
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameRandomNumbersFragment :
    BaseFragment<FragmentGameRandomNumbersBinding, GameRandomNumbersViewModel>(
        R.layout.fragment_game_random_numbers
    ) {
    override val binding by viewBinding(FragmentGameRandomNumbersBinding::bind)
    override val viewModel: GameRandomNumbersViewModel by activityViewModels()
    private val args: GameRandomNumbersFragmentArgs by navArgs()
    private var row: Int = 7
    private var last: Int = 14
    private var adapter: RandomNumbersAdapter? = null

    override fun initialize() {
        adapter = RandomNumbersAdapter()
        binding.rvGameRandomNumber.adapter = adapter
        viewModel.uploadRandomNumbers(args.quantitynumber)
    }

    override fun setupListeners() {
        setUpBtnBack()
        setUpBtnNextAndBtnPreviousListener()
        setUpBtnFinish()
    }

    private fun setUpBtnFinish() {
        binding.btnFinish.setOnSingleClickListener {
            findNavController().navigate(R.id.action_gameRandomNumbersFragment_to_answerRandomNumbersFragment)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpBtnNextAndBtnPreviousListener() {
        binding.btnNext.setOnSingleClickListener {
            row += 7
            last += 7
            adapter?.setNextAndPreviousItemRow(row, last)
            adapter?.notifyDataSetChanged()
            Log.e("anime", "Plus: $row")
        }
        binding.btnPrevious.setOnSingleClickListener {
            row -= 7
            last -= 7
            adapter?.setNextAndPreviousItemRow(row, last)
            Log.e("anime", "Minus: $row")
            adapter?.notifyDataSetChanged()
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
        viewModel.deleteRandomNumbers()
        adapter = null
    }
}