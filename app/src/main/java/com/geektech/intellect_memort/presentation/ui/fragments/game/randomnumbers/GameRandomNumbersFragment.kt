package com.geektech.intellect_memort.presentation.ui.fragments.game.randomnumbers

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentGameRandomNumbersBinding
import com.geektech.intellect_memort.presentation.adapters.RandomNumbersAdapter
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GameRandomNumbersFragment :
    BaseFragment<FragmentGameRandomNumbersBinding, GameRandomNumbersViewModel>(
        R.layout.fragment_game_random_numbers
    ) {
    override val binding by viewBinding(FragmentGameRandomNumbersBinding::bind)
    override val viewModel: GameRandomNumbersViewModel by viewModels()
    private val args: GameRandomNumbersFragmentArgs by navArgs()
    private val list = ArrayList<RandomNumbersModel>()
    private var row: Int = 1
    private val adapter = RandomNumbersAdapter()

    override fun initialize() {
        binding.rvGameRandomNumber.adapter = adapter
        binding.txtTimer.base = System.currentTimeMillis() + args.time.toLong()
        binding.txtTimer.start()
    }

    override fun setupListeners() {
        val random = List(args.quantitynumber) { Random().nextInt(9) }
        var index = 0

        random.forEach {
            index++
            list.addAll(listOf(RandomNumbersModel(it, index)))
            index--
        }
        adapter.submitList(list)
        setUpBtnBack()
        setUpBtnNextAndBtnPreviousListener()
    }

    private fun setUpBtnNextAndBtnPreviousListener() {
        binding.btnNext.setOnSingleClickListener {
            adapter.setNextAndPreviousItemRow(row + 7)
            Log.e("anime", "setUpBtnNextAndBtnPreviousListener: $row")
        }
        binding.btnPrevious.setOnSingleClickListener {
            adapter.setNextAndPreviousItemRow(row - 7)
        }
    }

    private fun setUpBtnBack() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigate(R.id.action_gameRandomNumbersFragment_to_homeFragment)
        }
    }


}