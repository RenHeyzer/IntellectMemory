package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.correctNumber
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.common.extension.timer
import com.geektech.intellect_memort.databinding.FragmentGameNumbersBinding
import com.geektech.intellect_memort.domain.models.NumbersModel
import com.geektech.intellect_memort.presentation.adapters.NumbersAdapter
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameNumbersFragment :
    BaseFragment<FragmentGameNumbersBinding, GameNumbersViewModel>(
        R.layout.fragment_game_numbers
    ) {

    override val binding by viewBinding(FragmentGameNumbersBinding::bind)
    override val viewModel: GameNumbersViewModel by viewModels()
    private val args: GameNumbersFragmentArgs by navArgs()
    private var row: Int = 0
    private var last: Int = 6
    private var adapter: NumbersAdapter? = null
    private var ascendingTimerAsString = ""
    private var countDownTimer: CountDownTimer? = null

    override fun initialize() {
        adapter = NumbersAdapter()
        binding.rvGameRandomNumber.adapter = adapter
    }

    override fun setupViews() {
        setupButtonPrevVisibility()
    }

    private fun setupButtonPrevVisibility() {
        binding.btnPrevious.isVisible = last != 14
    }

    override fun setupListeners() {
        setUpBtnBack()
        setUpBtnNextAndBtnPreviousListener()
        setUpBtnFinish()
    }

    private fun setUpBtnFinish() {
        val quantityNumber = args.quantitynumber
        binding.btnFinish.setOnSingleClickListener {
            countDownTimer?.cancel()
            findNavController().navigate(
                GameNumbersFragmentDirections.actionGameNumbersFragmentToAnswerNumbersFragment(
                    quantityNumber,
                    ascendingTimerAsString
                )
            )
        }
    }

    override fun setupRequests() {
        val correctQuantity = args.quantitynumber.correctNumber()
        Log.e("quantity", correctQuantity.toString())
        if (args.isBinary) {
            viewModel.generateBinaryNumbers(correctQuantity)
        } else {
            viewModel.generateRandomNumbers(correctQuantity)
        }
    }

    private fun setupTimer() {
        countDownTimer = timer(binding.txtTimer, args.time, {
            ascendingTimerAsString = it
        }, {
            if (ascendingTimerAsString.isNotEmpty()) {
                findNavController().navigate(
                    GameNumbersFragmentDirections.actionGameNumbersFragmentToAnswerNumbersFragment(
                        args.quantitynumber,
                        ascendingTimerAsString
                    )
                )
            }
        })
        countDownTimer?.start()
    }

    override fun setupObserves() {
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
                    viewModel.getAllRandomNumbers()
                    setupTimer()
                }
            }
        }

        viewModel.saveRandomNumbersState.subscribeIdle {
            adapter?.submitList(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpBtnNextAndBtnPreviousListener() {
        adapter?.setNextAndPreviousItemRow(row, last)
        binding.btnNext.setOnSingleClickListener {
            row += 7
            last += 7
            adapter?.setNextAndPreviousItemRow(row, last)
            adapter?.notifyDataSetChanged()
            Log.e("anime", "Plus: $last")
        }
        binding.btnPrevious.setOnSingleClickListener {
            row -= 7
            last -= 7
            adapter?.setNextAndPreviousItemRow(row, last)
            Log.e("anime", "Minus: $last")
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setUpBtnBack() {
        binding.btnBack.setOnSingleClickListener {
            Log.e("sizing", "fragment: ${adapter?.itemCount}")
            findNavController().navigate(
                GameNumbersFragmentDirections.actionGameNumbersFragmentToExitDialogFragment(false)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}