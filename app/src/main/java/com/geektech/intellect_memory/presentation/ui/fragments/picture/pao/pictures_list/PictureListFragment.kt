package com.geektech.intellect_memory.presentation.ui.fragments.picture.pao.pictures_list

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.base.BaseFragment
import com.geektech.intellect_memory.databinding.FragmentPictureListBinding
import com.geektech.intellect_memory.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureListFragment : BaseFragment<FragmentPictureListBinding, PictureListViewModel>
    (R.layout.fragment_picture_list) {

    override val binding by viewBinding(FragmentPictureListBinding::bind)
    override val viewModel: PictureListViewModel by viewModels()
    private var imageAdapter: PaoImageListAdapter? = null
    private val args: PictureListFragmentArgs by navArgs()
    private var isStop = false

    override fun initialize() {
        super.initialize()
        setupRecycler()
    }

    private fun setupRecycler() = binding.imageListRecycler.apply {
        val unscrollableGridLayout =
            object : GridLayoutManager(requireContext(), 2, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }

            }

        imageAdapter = PaoImageListAdapter()
        layoutManager = unscrollableGridLayout
        setHasFixedSize(true)
        adapter = imageAdapter

        val next = 7
        val back = 3

        // button next
        binding.btnNext.setOnClickListener {
            if (unscrollableGridLayout.findFirstVisibleItemPosition() + next < unscrollableGridLayout.itemCount) {
                unscrollableGridLayout.scrollToPosition(
                    unscrollableGridLayout.findFirstVisibleItemPosition() + next)
            } else if (
                unscrollableGridLayout.findLastVisibleItemPosition() == unscrollableGridLayout.itemCount ||
                unscrollableGridLayout.findLastVisibleItemPosition() + 1 == unscrollableGridLayout.itemCount
            ) {
                unscrollableGridLayout.scrollToPosition(
                    unscrollableGridLayout.itemCount - unscrollableGridLayout.itemCount + 1
                )
            } else {
                unscrollableGridLayout.scrollToPosition(unscrollableGridLayout.itemCount - 1)
            }
        }

        // button previous
        binding.btnPrevious.setOnClickListener {
            if (unscrollableGridLayout.findFirstVisibleItemPosition() > 0 && unscrollableGridLayout.findFirstVisibleItemPosition() - back > 0) {
                unscrollableGridLayout.scrollToPosition(unscrollableGridLayout.findFirstVisibleItemPosition() - back)
            } else {
                unscrollableGridLayout.scrollToPosition(1)
            }
        }
    }


    override fun setupObserves() {
        super.setupObserves()
        viewModel.picturesState.subscribe {
            when (it) {
                is UIState.Error -> {
                    Log.e("storage_load", "State Error: ${it.error}")
                }
                is UIState.Loading -> {
                    Log.e("storage_load", "State loading}")
                }
                is UIState.Success -> {
                    if (!isStop) {
                        val slicedList = it.data.subList(args.firstNumber, args.secondNumber + 1)
                        imageAdapter?.submitList(slicedList)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageAdapter = null
    }

    override fun onStop() {
        super.onStop()
        isStop = true
    }
}