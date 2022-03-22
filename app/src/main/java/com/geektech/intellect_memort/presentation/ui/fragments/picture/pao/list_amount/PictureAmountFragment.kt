package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao.list_amount

import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPictureAmountBinding
import com.geektech.intellect_memort.presentation.ui.fragments.picture.PictureViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureAmountFragment : BaseFragment<FragmentPictureAmountBinding, PictureAmountViewModel>(
    R.layout.fragment_picture_amount
) {
    override val binding by viewBinding(FragmentPictureAmountBinding::bind)
    override val viewModel: PictureAmountViewModel by viewModels()

    // game vars
    private var firstImageNumber: Int = 0
    private var secondImageNumber: Int = 0

    override fun initialize() {
        super.initialize()

        binding.amountFrom.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                firstImageNumber = it.toString().toInt()
            } else {
                firstImageNumber = 0
                Log.e("first_image_error", "error: ${it.toString()}")
            }
        }

        binding.amountTo.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                secondImageNumber = it.toString().toInt()
            } else {
                secondImageNumber = 0
                Log.e("second_image_error", "error: ${it.toString()}")
            }
        }
    }

    override fun setupListeners() {
        super.setupListeners()
        binding.btnStart.setOnClickListener {
            if (firstImageNumber < secondImageNumber) {
                val passImageNumbers =
                    PictureAmountFragmentDirections.actionPictureAmountFragmentToPictureListFragment(
                        firstImageNumber, secondImageNumber)
                findNavController().navigate(passImageNumbers)
            } else {
                Toast.makeText(requireContext(), "Ошибка ввода", Toast.LENGTH_SHORT).show()
            }
        }
    }

}