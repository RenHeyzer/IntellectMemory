package com.geektech.intellect_memort.presentation.ui.fragments.students.list

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.mainNavController
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memort.databinding.FragmentListOfStudentsBinding
import com.geektech.intellect_memort.presentation.ui.adapters.StudentsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListOfStudentsFragment : BaseFragment<FragmentListOfStudentsBinding, ListOfStudentsViewModel>(
    R.layout.fragment_list_of_students
) {

    @Inject
    lateinit var preferences: PreferencesHelper

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
        setupLogoutClickListener()
    }

    private fun addStudentsListener() {
        binding.btnAddStudents.setOnSingleClickListener {
            findNavController().navigate(R.id.action_listOfStudentsFragment_to_createStudentsFragment)
        }
    }

    private fun setupLogoutClickListener() {
        binding.btnLogout.setOnClickListener {
            preferences.isAdmin = false
            preferences.isOpenSignUp = true
            mainNavController().navigate(
                ListOfStudentsFragmentDirections.actionListOfStudentsFragmentToSignFlowFragment2()
            )
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