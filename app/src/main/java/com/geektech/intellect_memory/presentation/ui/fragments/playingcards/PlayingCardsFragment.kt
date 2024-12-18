package com.geektech.intellect_memory.presentation.ui.fragments.playingcards

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MotionEvent
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.base.BaseFragment
import com.geektech.intellect_memory.common.extension.navigateSafely
import com.geektech.intellect_memory.common.extension.overrideOnBackPressed
import com.geektech.intellect_memory.common.extension.setOnSingleClickListener
import com.geektech.intellect_memory.databinding.FragmentPlayingCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingCardsFragment :
    BaseFragment<FragmentPlayingCardsBinding, PlayingCardsViewModel>(
        R.layout.fragment_playing_cards
    ) {
    override val binding by viewBinding(FragmentPlayingCardsBinding::bind)
    override val viewModel: PlayingCardsViewModel by hiltNavGraphViewModels(R.id.main_graph)
    private var isClickedBtn1 = false
    private var isClickedBtn2 = false
    private var isClickedBtn3 = false
    private var isClickedBtn4 = false
    private var brick = ""
    private var clover = ""
    private var piqui = ""
    private var redHeart = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun setupListeners() = with(binding) {
        setUpBtnBack()
        btnCards1.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    isClickedBtn1 = if (!isClickedBtn1) {
                        cardsBtn1.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F1C256")))
                        clover = "clover"
                        true
                    } else {
                        cardsBtn1.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        clover = ""
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
                        redHeart = "red_heart"
                        true
                    } else {
                        cardsBtn2.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        redHeart = ""
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
                        piqui = "piqui"
                        true
                    } else {
                        cardsBtn3.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        piqui = ""
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
                        brick = "brick"
                        true
                    } else {
                        cardsBtn4.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
                        brick = ""
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

    private fun setUpBtnBack() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
        overrideOnBackPressed {
            findNavController().navigateUp()
        }
    }

    private fun setUpBtnChoose() {
        if (isClickedBtn1 || isClickedBtn2 || isClickedBtn3 || isClickedBtn4
        ) {
            viewModel.fetchImageOfCards(
                typeClover = clover,
                typeBrick = redHeart,
                typePiqui = piqui,
                typeRedHeard = brick
            )
            findNavController().navigateSafely(
                PlayingCardsFragmentDirections
                    .actionPlayingCardsFragmentToChooseTheNumberOfCardsFragment(
                        isClover = clover,
                        isredHeart = redHeart,
                        ispiqui = piqui,
                        isbrick = brick
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
        brick = ""
        clover = ""
        redHeart = ""
        piqui = ""
        isClickedBtn1 = false
        isClickedBtn2 = false
        isClickedBtn3 = false
        isClickedBtn4 = false
    }
}