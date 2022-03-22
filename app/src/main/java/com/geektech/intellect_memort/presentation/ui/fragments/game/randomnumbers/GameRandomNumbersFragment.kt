package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
    }

    override fun setupListeners() {
        setUpBtnBack()
        setUpBtnNextAndBtnPreviousListener()
        setUpBtnFinish()
    }

    override fun setupRequests() {
        viewModel.generateRandomNumbers(args.quantitynumber)
        viewModel.randomNumbersState.subscribe {
            when (it) {
                is UIState.Error -> {
                    Log.e("anime", "Error randomNumbers:${it.error} ")
                }
                is UIState.Loading -> {
                    Log.e("anime", "Loading randomNumbers $it")
                }
                is UIState.Success -> {
                    Log.e("anime", "Success randomNumbers:${it.data} ")
                    if (viewModel.saveRandomNumbersState.value.isEmpty()) {
                        viewModel.insertAllRandomNumbers(it.data)
                    }
                }
            }
        }
        viewModel.getAllRandomNumbers()
    }

    private fun setUpBtnFinish() {
        val quantityNumber = args.quantitynumber
        binding.btnFinish.setOnSingleClickListener {
            findNavController().navigate(
                GameRandomNumbersFragmentDirections.actionGameRandomNumbersFragmentToAnswerRandomNumbersFragment(
                    quantityNumber
                )
            )
        }
    }

    override fun setupObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveRandomNumbersState.collect {
                    adapter?.submitList(it)
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
        adapter = null
    }
}