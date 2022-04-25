package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
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
                etTime.text?.isEmpty() == true -> {
                    Toast.makeText(requireContext(), "Заполните Поле!", Toast.LENGTH_SHORT).show()
                }
                etTime.text.toString().toInt() == 0 || etTime.text.toString().toInt() < 0 -> {
                    Toast.makeText(requireContext(), "Заполните Поле!", Toast.LENGTH_SHORT).show()
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
                                numbersOfCards = args.numbersOfCards
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