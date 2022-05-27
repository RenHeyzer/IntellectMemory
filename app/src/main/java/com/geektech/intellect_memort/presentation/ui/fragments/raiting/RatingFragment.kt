package com.geektech.intellect_memort.presentation.ui.fragments.raiting

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.overrideOnBackPressed
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memort.databinding.FragmentRatingBinding
import com.geektech.intellect_memort.presentation.ui.adapters.StudentsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding, RatingViewModel>(
    R.layout.fragment_rating
) {

    @Inject
    lateinit var preferences: PreferencesHelper

    override val binding by viewBinding(FragmentRatingBinding::bind)
    override val viewModel: RatingViewModel by viewModels()
    private val studentsAdapter = StudentsAdapter()

    override fun initialize() {
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.studentsRecycler.layoutManager = LinearLayoutManager(context)
        binding.studentsRecycler.adapter = studentsAdapter
    }

    override fun setupListeners() {
        setupSearchStudents()
        setupPlaceRating()
        setupBackPressed()
    }

    private fun setupSearchStudents() = with(binding) {
        searchStudents.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && p0.toString().trim().isNotEmpty()) {
                    if (btnSchoolRating.isChecked) {
                        lifecycleScope.launchWhenStarted {
                            viewModel.searchStudents(fullName = p0.toString().trim(),
                                preferences.school).collect {
                                studentsAdapter.submitData(it)
                            }
                        }
                    } else {
                        lifecycleScope.launchWhenStarted {
                            viewModel.searchStudents(fullName = p0.toString().trim(), null)
                                .collect {
                                    studentsAdapter.submitData(it)
                                }
                        }
                    }
                } else {
                    if (btnSchoolRating.isChecked) {
                        viewModel.getListOfStudents(preferences.school)
                    } else {
                        viewModel.getListOfStudents(null)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setupPlaceRating() = with(binding) {
        btnGlobalRating.isChecked = true
        btnGlobalRating.setOnCheckedChangeListener { _, b ->
            if (b) {
                btnGlobalRating.isChecked = true
                viewModel.getListOfStudents(null)
                btnSchoolRating.isChecked = false
            }
        }
        btnSchoolRating.setOnCheckedChangeListener { _, b ->
            if (b) {
                btnSchoolRating.isChecked = true
                viewModel.getListOfStudents(preferences.school)
                btnGlobalRating.isChecked = false
            }
        }
    }

    private fun setupBackPressed() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
        overrideOnBackPressed {
            findNavController().navigateUp()
        }
    }

    override fun setupRequests() {
        if (binding.btnGlobalRating.isChecked) {
            viewModel.getListOfStudents(null)
        }
    }

    override fun setupObserves() {
        viewModel.studentsState.subscribePaging {
            lifecycleScope.launchWhenStarted {
                studentsAdapter.submitData(it)
            }
            Log.e("fire", "${studentsAdapter.snapshot().items}")
        }
    }
}