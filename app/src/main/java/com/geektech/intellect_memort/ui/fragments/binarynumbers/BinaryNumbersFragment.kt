package com.geektech.intellect_memort.ui.fragments.binarynumbers

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentBinaryNumbersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BinaryNumbersFragment : BaseFragment<FragmentBinaryNumbersBinding, BinaryNumbersViewModel>(
    R.layout.fragment_binary_numbers
) {
    override val binding by viewBinding(FragmentBinaryNumbersBinding::bind)
    override val viewModel: BinaryNumbersViewModel by viewModels()

}