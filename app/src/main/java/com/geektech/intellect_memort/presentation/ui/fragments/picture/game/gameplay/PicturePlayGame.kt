package com.geektech.intellect_memort.presentation.ui.fragments.picture.game.gameplay

import android.app.AlertDialog
import android.os.CountDownTimer
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.databinding.FragmentPicturePlayGameBinding
import com.geektech.intellect_memort.domain.models.PictureImageModel
import com.geektech.intellect_memort.domain.models.PictureImagesList
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat

@AndroidEntryPoint
class PicturePlayGame : BaseFragment<FragmentPicturePlayGameBinding, PicturePlayGameViewModel>
    (R.layout.fragment_picture_play_game) {

    override val binding by viewBinding(FragmentPicturePlayGameBinding::bind)
    override val viewModel: PicturePlayGameViewModel by viewModels()
    private var imageAdapter: PlayImageListAdapter? = null
    private val args: PicturePlayGameArgs by navArgs()

    // game vars
    private var allImagesList: List<PictureImageModel> = mutableListOf()
    private var slicedImageList: List<PictureImageModel> = mutableListOf()
    private var ascendingTimerAsString: String = ""
    private var timer: CountDownTimer? = null
    private var isStop = false

    override fun initialize() {
        super.initialize()
        setupRecycler()
        navigationToMenu()
        startTimeCounter(binding.timer, args.timeNumber)


    }

    private fun navigationToMenu() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialog()
                }
            })
    }

    override fun setupListeners() {
        super.setupListeners()
        binding.btnBack.setOnClickListener {
            showAlertDialog()
        }
        binding.btnFinish.setOnClickListener {
            if (slicedImageList.isNotEmpty() && ascendingTimerAsString.isNotBlank()) {
                showAlertDialogFinish()
            }
        }
    }

    override fun setupObserves() {
        viewModel.picturesState.subscribe {
            when (it) {
                is UIState.Error -> {
                    Toast.makeText(requireContext(),
                        "pictures error${it.error}",
                        Toast.LENGTH_LONG).show()
                    Log.e("storage_load", "State Error: ${it.error}")
                }
                is UIState.Loading -> {
                    Log.e("storage_load", "State loading}")
                }
                is UIState.Success -> {
                    if (!isStop) {
                        allImagesList = it.data
                        imageAdapter?.submitList(sliceImageList(allImagesList))
                    }
                }
            }
        }
    }

    private fun setupRecycler() = binding.imagesList.apply {
        val unscrollableGridLayout =
            object : GridLayoutManager(requireContext(), 2, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }

            }

        imageAdapter = PlayImageListAdapter()
        layoutManager = unscrollableGridLayout
        setHasFixedSize(true)
        adapter = imageAdapter


        val next = 7
        val back = 3

        // button next
        binding.btnNext.setOnClickListener {
            if (slicedImageList.isNotEmpty()) {
                if (unscrollableGridLayout.findFirstVisibleItemPosition() + next < unscrollableGridLayout.itemCount) {
                    unscrollableGridLayout.scrollToPosition(unscrollableGridLayout.findFirstVisibleItemPosition() + next)
                } else {
                    binding.btnNext.visibility = GONE
                    binding.btnFinishMemorizing.visibility = VISIBLE
                    unscrollableGridLayout.scrollToPosition(unscrollableGridLayout.itemCount - 1)
                }
            }
        }

        // button previous
        binding.btnPrevious.setOnClickListener {
            if (unscrollableGridLayout.findFirstVisibleItemPosition() > 0 && unscrollableGridLayout.findFirstVisibleItemPosition() - back > 0) {
                unscrollableGridLayout.scrollToPosition(unscrollableGridLayout.findFirstVisibleItemPosition() - back)
                binding.btnNext.visibility = VISIBLE
                binding.btnFinishMemorizing.visibility = GONE
            } else {
                binding.btnNext.visibility = VISIBLE
                binding.btnFinishMemorizing.visibility = GONE
                unscrollableGridLayout.scrollToPosition(1)
            }
        }

        binding.btnFinishMemorizing.setOnClickListener {
            if (slicedImageList.isNotEmpty() &&
                allImagesList.isNotEmpty() &&
                ascendingTimerAsString.isNotEmpty()
            ) {
                timer?.cancel()
                findNavController().navigateSafely(PicturePlayGameDirections.actionPicturePlayGameToPicturePlayGameAnswering(
                    PictureImagesList(slicedImageList),
                    ascendingTimerAsString
                ))
            }
        }
    }

    private fun sliceImageList(allImages: List<PictureImageModel>): List<PictureImageModel> {
        val number = args.rangeNumber
        val numberOfImagesInList = 9
        val newList: MutableList<PictureImageModel> = mutableListOf()

        if (number != 0) {
            for (i in number * 10..number * 10 + numberOfImagesInList) {
                newList.add(allImages[i])
            }
        } else {
            for (i in number..number + numberOfImagesInList) {
                newList.add(allImages[i])
            }
        }
        slicedImageList = newList
        return newList
    }


    private fun showAlertDialog() {
        findNavController().navigateSafely(PicturePlayGameDirections
            .actionPicturePlayGameToExitDialogFragment())
    }

    private fun showAlertDialogFinish() {
        AlertDialog.Builder(requireContext()).setMessage("Начать квиз?")
            .setPositiveButton("Да") { _, _ ->
                timer?.cancel()
                findNavController().navigateSafely(PicturePlayGameDirections
                    .actionPicturePlayGameToPicturePlayGameAnswering(
                        imagesList = PictureImagesList(slicedImageList),
                        passedTime = ascendingTimerAsString
                    ))
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }


    private fun startTimeCounter(textView: TextView, time: Int) {
        timer = object : CountDownTimer((time * 60000).toLong(), 1000) {
            // timer ascending
            var ascendingTimer: Int = 0
            val decimal: NumberFormat = DecimalFormat("00")

            override fun onTick(millisUntilFinished: Long) {
                // timer descending
                val minute = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                ascendingTimer++
                val minuteAscending: Int = (ascendingTimer / 60)
                val secondsAscending: Int = ascendingTimer

                textView.text =
                    getString(R.string.timer_template,
                        decimal.format(minute).toString(),
                        decimal.format(seconds).toString())

                ascendingTimerAsString = getString(R.string.timer_template,
                    decimal.format(minuteAscending).toString(),
                    decimal.format(secondsAscending).toString())

            }

            override fun onFinish() {
                val minuteAscending: Int = (ascendingTimer / 60)
                val secondsAscending: Int = ascendingTimer

                ascendingTimerAsString =
                    getString(R.string.timer_template,
                        decimal.format(minuteAscending).toString(),
                        decimal.format(secondsAscending).toString())

                if (ascendingTimerAsString.isNotEmpty()) {
                    findNavController().navigateSafely(PicturePlayGameDirections
                        .actionPicturePlayGameToPicturePlayGameAnswering(
                            PictureImagesList(slicedImageList),
                            ascendingTimerAsString
                        ))
                }

            }

        }
        timer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageAdapter = null
    }

    override fun onStop() {
        super.onStop()
        isStop = true
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}