package com.geektech.intellect_memort.ui.fragments.randomnumbers

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentRandomNumbersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomNumbersFragment : BaseFragment<FragmentRandomNumbersBinding, RandomNumbersViewModel>(
    R.layout.fragment_random_numbers
) {
    override val binding by viewBinding(FragmentRandomNumbersBinding::bind)
    override val viewModel: RandomNumbersViewModel by viewModels()

}