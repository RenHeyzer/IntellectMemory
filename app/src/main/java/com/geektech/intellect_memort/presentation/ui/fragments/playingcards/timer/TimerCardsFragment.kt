package com.geektech.intellect_memort.presentation.ui.fragments.playingcards.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.databinding.FragmentTimerCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerCardsFragment : Fragment() {
    private var _binding: FragmentTimerCardsBinding? = null
    private val binding get() = _binding!!
    private val args: TimerCardsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTimerCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTimerTickListener()
    }

    private fun setUpTimerTickListener() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtTimer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                findNavController().navigateSafely(
                    TimerCardsFragmentDirections.actionTimerCardsFragmentToPlayingCardsGameFragment(
                        time = args.time,
                        timeForMemoryCard = args.timeForMemoryCard,
                        isClover = args.isClover,
                        ispiqui = args.ispiqui,
                        isredHeart = args.isredHeart,
                        isbrick = args.isbrick,
                        numbersOfCards = args.numbersOfCards,

                        )
                )
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}