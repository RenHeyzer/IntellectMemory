package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao

import android.util.Log
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
class PictureAmountFragment : BaseFragment<FragmentPictureAmountBinding, PictureViewModel>(
    R.layout.fragment_picture_amount
) {
    override val binding by viewBinding(FragmentPictureAmountBinding::bind)
    override val viewModel: PictureViewModel by viewModels()

    private var firstImageNumber: Int = 0
    private var secondImageNumber: Int = 0

    override fun initialize() {
        super.initialize()

        binding.amountFrom.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                firstImageNumber = it.toString().toInt()
            } else {
                Log.e("first_image_error", "error: ${it.toString()}")
            }
        }

        binding.amountTo.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                secondImageNumber = it.toString().toInt()
            } else {
                Log.e("second_image_error", "error: ${it.toString()}")
            }
        }
    }

    override fun setupListeners() {
        super.setupListeners()
        binding.btnStart.setOnClickListener {
            val passImageNumbers =
                PictureAmountFragmentDirections.actionPictureAmountFragmentToPictureListFragment(
                    firstImageNumber, secondImageNumber)
            findNavController().navigate(passImageNumbers)
        }
    }

}