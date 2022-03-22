package com.geektech.intellect_memort.presentation.ui.fragments.picture

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPictureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureFragment : BaseFragment<FragmentPictureBinding, PictureViewModel>(
    R.layout.fragment_picture
) {
    override val binding by viewBinding(FragmentPictureBinding::bind)
    override val viewModel: PictureViewModel by viewModels()


    override fun setupListeners() {
        super.setupListeners()
        binding.btnPao.setOnClickListener {
            findNavController().navigate(R.id.pictureAmountFragment)
        }
        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.picturePlayAmount)
        }
    }

}