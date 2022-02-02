package com.geektech.intellect_memort.presentation.ui.fragments.students.list

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentListOfStudentsBinding
import com.geektech.intellect_memort.presentation.ui.adapters.StudentsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfStudentsFragment : BaseFragment<FragmentListOfStudentsBinding, ListOfStudentsViewModel>(
    R.layout.fragment_list_of_students
) {
    override val binding by viewBinding(FragmentListOfStudentsBinding::bind)
    override val viewModel by viewModels<ListOfStudentsViewModel>()
    private val studentsAdapter = StudentsAdapter()

    override fun initialize() {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        val linearLayoutManager = LinearLayoutManager(context)
        studentsRecycler.layoutManager = linearLayoutManager
        studentsRecycler.adapter = studentsAdapter
    }

    override fun setupListeners() {
        addStudentsListener()
    }

    private fun addStudentsListener() {
        binding.btnAddStudents.setOnSingleClickListener {
            findNavController().navigate(R.id.action_listOfStudentsFragment_to_createStudentsFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setupObserves() {
        viewModel.studentsState.subscribePaging {
            lifecycleScope.launchWhenStarted {
                studentsAdapter.submitData(it)
                studentsAdapter.notifyDataSetChanged()
            }
        }
    }
}