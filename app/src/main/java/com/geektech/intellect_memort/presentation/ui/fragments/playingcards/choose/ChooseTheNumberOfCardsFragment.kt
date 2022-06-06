package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memort.common.extension.hideKeyboard
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.common.extension.toast
import com.geektech.intellect_memort.databinding.FragmentChooseTheNumberOfCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseTheNumberOfCardsFragment : Fragment() {
    private var _binding: FragmentChooseTheNumberOfCardsBinding? = null
    private val binding get() = _binding!!
    private var firstClick = false
    private var timeForMemoryOnCard: Int = 0
    private val args: ChooseTheNumberOfCardsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChooseTheNumberOfCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setUpListeners()
    }

    private fun initialize() {}
    private fun setUpListeners() = with(binding) {
        txtChooseTheNumbersOfCardsMax.setOnSingleClickListener {
            txtChooseTheNumbersOfCardsMax.text = "1"
            txtChooseTheNumbersOfCardsMin.text = "3"
        }
        txtChooseTheNumbersOfCardsMin.setOnSingleClickListener {
            txtChooseTheNumbersOfCardsMin.text = "1"
            txtChooseTheNumbersOfCardsMax.text = "3"
        }
        btnFurther.setOnSingleClickListener {
            if (!firstClick) {
                materialCardView.visibility = GONE
                txtChooseGame.visibility = GONE
                txtText.visibility = VISIBLE
                txtMinuts.visibility = VISIBLE
                etTime.visibility = VISIBLE
                firstClick = true
            } else if (firstClick) {
                when {
                    etTime.text.toString().toInt() > 61 -> {
                        toast("Максимальное время для Запоминания 60 Секунд")
                    }
                    etTime.text.toString().toInt() <= 0 -> {
                        toast("Минимальное время для Запоминания 1 Секунда")
                    }
                    etTime.text.length > 2 -> {
                        toast("Время для Запоминания от 1 до 60 Секунда")
                    }
                    etTime.text == null || etTime.text.toString() == " " -> {
                        toast("Введите время!")
                    }
                    etTime.text.first().toString().toInt() == 0 -> {
                        toast("Введите Корректно!")
                    }
                    else -> {
                        timeForMemoryOnCard = etTime.text.toString().toInt()
                        etTime.hideKeyboard()
                        findNavController().navigateSafely(ChooseTheNumberOfCardsFragmentDirections.actionChooseTheNumberOfCardsFragmentToInputTimeForAnswerFragment(
                            timeForMemoryCards = timeForMemoryOnCard,
                            numbersOfCards = txtChooseTheNumbersOfCardsMin.text.toString().toInt(),
                            isbrick = args.isbrick,
                            isredHeart = args.isredHeart,
                            ispiqui = args.ispiqui,
                            isClover = args.isClover
                        ))
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}