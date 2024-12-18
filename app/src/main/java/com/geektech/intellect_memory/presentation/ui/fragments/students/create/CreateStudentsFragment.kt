package com.geektech.intellect_memory.presentation.ui.fragments.students.create

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.base.BaseFragment
import com.geektech.intellect_memory.common.extension.setOnSingleClickListener
import com.geektech.intellect_memory.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memory.databinding.FragmentCreateStudentsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateStudentsFragment : BaseFragment<FragmentCreateStudentsBinding, CreateStudentsViewModel>(
    R.layout.fragment_create_students
) {
    override val binding by viewBinding(FragmentCreateStudentsBinding::bind)
    override val viewModel by viewModels<CreateStudentsViewModel>()

    @Inject
    lateinit var preferences: PreferencesHelper

    override fun setupListeners() {
        setOnCreateClickListener()
        setOnGeneratePasswordClickListener()
    }

    private fun setOnGeneratePasswordClickListener() = with(binding) {
        generatePassword.setOnSingleClickListener {
            val password = UUID.randomUUID().toString().substring(0, 10)
            etPassword.setText(password)
        }
    }

    private fun setOnCreateClickListener() = with(binding) {

        btnCreateUser.setOnSingleClickListener {
            val userId = "User${UUID.randomUUID().toString().substring(0, 10)}"
            val fullName = etFullName.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val teacher = etTeacher.text.toString().trim()
            val branch = etBranch.text.toString().trim()
            val location = etLocation.text.toString().trim()

            if (fullName.isEmpty() || password.isEmpty() || teacher.isEmpty() || branch.isEmpty() || location.isEmpty()) {
                fullNameInputLayout.error = getString(R.string.edittext_error_message)
                passwordInputLayout.error = getString(R.string.edittext_error_message)
                etTeacherInputLayout.error = getString(R.string.edittext_error_message)
                branchInputLayout.error = getString(R.string.edittext_error_message)
                locationInputLayout.error = getString(R.string.edittext_error_message)
            } else {
                val student = hashMapOf<String, Any>(
                    "id" to userId,
                    "fullName" to fullName,
                    "password" to password,
                    "teacher" to teacher,
                    "branch" to branch,
                    "location" to location,
                    "points" to 0
                )
                lifecycleScope.launch {
                    viewModel.createStudent(student, userId)
                }
                findNavController().navigateUp()
            }
        }
    }
}