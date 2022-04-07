package com.geektech.intellect_memort.presentation.ui.fragments.playingcards

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MotionEvent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentPlayingCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingCardsFragment :
    BaseFragment<FragmentPlayingCardsBinding, PlayingCardsViewModel>(
        R.layout.fragment_playing_cards
    ) {
    override val binding by viewBinding(FragmentPlayingCardsBinding::bind)
    override val viewModel: PlayingCardsViewModel by viewModels()
    private var isClickedBtn1 = false
    private var isClickedBtn2 = false
    private var isClickedBtn3 = false
    private var isClickedBtn4 = false

    @SuppressLint("ClickableViewAccessibility")
    override fun setupListeners() = with(binding) {
        btnCards1.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    isClickedBtn1 = if (!isClickedBtn1) {
                        cardsBtn1.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F1C256")))
                        true
                    } else {
                        cardsBtn1.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        false
                    }

                }
                MotionEvent.ACTION_UP -> {
                }
            }
            true
        }
        btnCards2.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    isClickedBtn2 = if (!isClickedBtn2) {
                        cardsBtn2.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F1C256")))
                        true
                    } else {
                        cardsBtn2.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        false
                    }
                }
                MotionEvent.ACTION_UP -> {
                }
            }
            true
        }
        btnCards3.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    isClickedBtn3 = if (!isClickedBtn3) {
                        cardsBtn3.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F1C256")))
                        true
                    } else {
                        cardsBtn3.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        false
                    }
                }
                MotionEvent.ACTION_UP -> {
                }

            }
            true
        }
        btnCards4.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    isClickedBtn4 = if (!isClickedBtn4) {
                        cardsBtn4.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F1C256")))
                        true
                    } else {
                        cardsBtn4.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        false
                    }
                }
                MotionEvent.ACTION_UP -> {
                }
            }
            true
        }
        binding.btnChoose.setOnSingleClickListener {
            setUpBtnChoose()
        }
    }

    private fun setUpBtnChoose() {
        if (isClickedBtn1 || isClickedBtn2 || isClickedBtn3 || isClickedBtn4
        ) {
            findNavController().navigateSafely(
                PlayingCardsFragmentDirections
                    .actionPlayingCardsFragmentToChooseTheNumberOfCardsFragment(
                        isClover = isClickedBtn1,
                        isredHeart = isClickedBtn2,
                        ispiqui = isClickedBtn3,
                        isbrick = isClickedBtn4
                    )
            )
        } else if (!isClickedBtn1 || !isClickedBtn2 || !isClickedBtn3 || !isClickedBtn4) {
            Toast.makeText(
                requireContext(),
                "Выберите Одну из Колод",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isClickedBtn1 = false
        isClickedBtn2 = false
        isClickedBtn3 = false
        isClickedBtn4 = false
    }
}