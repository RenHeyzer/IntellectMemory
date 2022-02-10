package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPictureListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PictureListFragment : BaseFragment<FragmentPictureListBinding, PictureListViewModel>
    (R.layout.fragment_picture_list) {

    override val binding by viewBinding(FragmentPictureListBinding::bind)
    override val viewModel: PictureListViewModel by viewModels()
    private val imageAdapter by lazy { PaoImageListAdapter() }

    private val args: PictureListFragmentArgs by navArgs()

    override fun initialize() {
        super.initialize()
        setupRecycler()
    }

    override fun setupObserves() {
        super.setupObserves()
        lifecycleScope.launch {
           imageAdapter.differ.submitList(viewModel.fetchimages())
        }
    }

    private fun setupRecycler() = binding.imageListRecycler.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = imageAdapter

    }





}