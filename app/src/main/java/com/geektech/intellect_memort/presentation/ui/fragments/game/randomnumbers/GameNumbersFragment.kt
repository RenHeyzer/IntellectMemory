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
import com.geektech.intellect_memort.common.extension.*
import com.geektech.intellect_memort.databinding.FragmentGameNumbersBinding
import com.geektech.intellect_memort.presentation.state.UIState
import com.geektech.intellect_memort.presentation.ui.adapters.NumbersAdapter
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
    private var isStop = false

    override fun initialize() {
        setupTimerSplash()
        adapter = NumbersAdapter()
        binding.rvGameRandomNumber.adapter = adapter
    }

    private fun setupTimerSplash() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerSplash.txtTimerSplash.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.timerSplash.root.hide()
                setupTimer()
            }
        }
        timer.start()
    }

    override fun setupViews() {
        binding.btnPrevious.isVisible = false
        binding.btnNext.isVisible = false
        binding.btnEndFinish.isVisible = false
        binding.btnEndNext.isVisible = true
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
            findNavController().navigateSafely(
                GameNumbersFragmentDirections.actionGameNumbersFragmentToAnswerNumbersFragment(
                    quantityNumber,
                    ascendingTimerAsString
                )
            )
        }
        binding.btnEndFinish.setOnSingleClickListener {
            countDownTimer?.cancel()
            findNavController().navigateSafely(
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
                findNavController().navigateSafely(
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
                }
            }
        }
        viewModel.saveRandomNumbersState.subscribeIdle {
            adapter?.submitList(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpBtnNextAndBtnPreviousListener() = with(binding) {
        adapter?.setNextAndPreviousItemRow(row, last)
        btnNext.setOnSingleClickListener {
            row += 7
            last += 7
            setupButtonsPrevAndNextVisibility()
            adapter?.setNextAndPreviousItemRow(row, last)
            adapter?.notifyDataSetChanged()
            Log.e("anime", "Plus: $last")
        }
        btnEndNext.setOnSingleClickListener {
            row += 7
            last += 7
            setupButtonsPrevAndNextVisibility()
            adapter?.setNextAndPreviousItemRow(row, last)
            adapter?.notifyDataSetChanged()
            Log.e("anime", "Plus: $last")
        }
        btnPrevious.setOnSingleClickListener {
            row -= 7
            last -= 7
            setupButtonsPrevAndNextVisibility()
            adapter?.setNextAndPreviousItemRow(row, last)
            Log.e("anime", "Minus: $last")
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setupButtonsPrevAndNextVisibility() = with(binding) {
        adapter?.itemCount?.let {
            when {
                last != 6 && last < it -> {
                    Log.e("lasteed", "1")
                    btnPrevious.isVisible = true
                    btnNext.isVisible = true
                    btnEndNext.isVisible = false
                    btnEndFinish.isVisible = false
                }
                last != 6 && last > it -> {
                    btnPrevious.isVisible = false
                    btnNext.isVisible = false
                    Log.e("lasteed", "2")
                    btnEndNext.isVisible = false
                    btnEndFinish.isVisible = true
                }
                last == 6 && last < it -> {
                    Log.e("lasteed", "else")
                    btnPrevious.isVisible = false
                    btnNext.isVisible = false
                    btnEndNext.isVisible = true
                    btnEndFinish.isVisible = false
                }
            }
        }
        Log.e("nummers", adapter?.itemCount.toString())
    }

    private fun setUpBtnBack() {
        binding.btnBack.setOnSingleClickListener {
            Log.e("sizing", "fragment: ${adapter?.itemCount}")
            findNavController().navigateSafely(
                GameNumbersFragmentDirections.actionGameNumbersFragmentToExitDialogFragment(false)
            )
        }
        overrideOnBackPressed {
            findNavController().navigateSafely(
                GameNumbersFragmentDirections.actionGameNumbersFragmentToExitDialogFragment(false)
            )
        }
    }

    override fun onStop() {
        super.onStop()
        isStop = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        countDownTimer?.cancel()
        countDownTimer = null
    }
}