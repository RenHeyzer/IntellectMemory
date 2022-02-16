package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPictureListBinding
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureListFragment : BaseFragment<FragmentPictureListBinding, PictureListViewModel>
    (R.layout.fragment_picture_list) {

    override val binding by viewBinding(FragmentPictureListBinding::bind)
    override val viewModel: PictureListViewModel by viewModels()
    private var imageAdapter: PaoImageListAdapter? = null

    override fun initialize() {
        super.initialize()
        setupRecycler()
    }

    private fun setupRecycler() = binding.imageListRecycler.apply {
        imageAdapter = PaoImageListAdapter()
        adapter = imageAdapter

    }

    override fun setupObserves() {
        super.setupObserves()
        viewModel.picturesState.subscribe {
            when (it) {
                is UIState.Error -> {
                    Toast.makeText(requireContext(),
                        "pictures error${it.error}",
                        Toast.LENGTH_LONG).show()
                    Log.e("anime", "State Error: ${it.error}", )
                }
                is UIState.Loading -> {
                    Log.e("anime", "State loading}", )
                }
                is UIState.Success -> {
                    it.data.forEach { images ->
                        Log.e("anime","State Success: ${images.imageUrl}")
                    }
                    imageAdapter?.submitList(it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageAdapter = null
    }
}