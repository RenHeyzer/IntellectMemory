package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.common.extension.toast
import com.geektech.intellect_memort.databinding.FragmentInputTimeForAnswerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputTimeForAnswerFragment : Fragment() {
    private var _binding: FragmentInputTimeForAnswerBinding? = null
    private val binding get() = _binding!!
    private val args: InputTimeForAnswerFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInputTimeForAnswerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        setUpBtnStart()
    }

    private fun setUpBtnStart() = with(binding) {
        btnFurther.setOnSingleClickListener {
            when {
                etTime.text.toString().toInt() > 61 -> {
                    toast("Максимальное время для Запоминания 60 Минут")
                }
                etTime.text.toString().toInt() <= 0 -> {
                    toast("Минимальное время для Запоминания 1 Минут")
                }
                etTime.text.length > 2 -> {
                    toast("Время для Запоминания от 1 до 60 Минут")
                }
                etTime.text == null || etTime.text.toString() == " " -> {
                    toast("Введите время!")
                }
                etTime.text.first().toString().toInt() == 0 -> {
                    toast("Введите Корректно!")


                }
                else -> {
                    findNavController().navigateSafely(
                        InputTimeForAnswerFragmentDirections
                            .actionInputTimeForAnswerFragmentToPlayingCardsGameFragment(
                                time = etTime.text.toString().toInt(),
                                timeForMemoryCard = args.timeForMemoryCards,
                                isClover = args.isClover,
                                ispiqui = args.ispiqui,
                                isredHeart = args.isredHeart,
                                isbrick = args.isbrick,
                                numbersOfCards = args.numbersOfCards,
                            ))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}