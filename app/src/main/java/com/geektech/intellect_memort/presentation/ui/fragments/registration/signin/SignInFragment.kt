package com.geektech.intellect_memort.presentation.ui.fragments.registration.signin

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.mainNavController
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.common.extension.showDialog
import com.geektech.intellect_memort.common.utils.Localization
import com.geektech.intellect_memort.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memort.databinding.FragmentSignInBinding
import com.geektech.intellect_memort.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>(
    R.layout.fragment_sign_in
) {
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<SignInViewModel>()
    private var dialogProgressbar: Dialog? = null

    @Inject
    lateinit var preferences: PreferencesHelper

    override fun setupListeners() {
        setOnSwitchClickListener()
    }

    override fun setupViews() {
        binding.btnEnglish.isChecked = preferences.getLanguage() == "en"
    }

    private fun setOnSwitchClickListener() = with(binding) {
        switchLanguage.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.btn_english -> {
                    setLocale(Localization.ENGLISH)
                    btnRussian.isChecked = false
                }
                R.id.btn_russian -> {
                    setLocale(Localization.RUSSIAN)
                    btnEnglish.isChecked = false
                }
            }
        }
    }

    override fun setupObserves() = with(binding) {

        signInButton.setOnSingleClickListener { _ ->
            showProgressbar()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            viewModel.adminsState.subscribe {
                when (it) {
                    is UIState.Error -> {
                        Log.e("more-error", it.error)
                    }
                    is UIState.Loading -> {
                        Log.e("more-error", "loading")
                    }
                    is UIState.Success -> {
                        it.data.forEach { admin ->
                            when {
                                password.isEmpty() && username.isEmpty() -> {
                                    inputUsername.error =
                                        getString(R.string.edittext_error_message)
                                    inputPassword.error =
                                        getString(R.string.edittext_error_message)
                                    dialogProgressbar?.dismiss()
                                }
                                username == admin?.fullName && password == admin.password -> {
                                    isAdmin()
                                    dialogProgressbar?.dismiss()
                                    mainNavController().navigate(R.id.action_signInFragment_to_mainFlowFragment)
                                    wasOpen()
                                }
                                else -> {
                                    dialogProgressbar?.dismiss()
                                }
                            }
                        }
                    }
                }
            }

            viewModel.studentsState.subscribe {
                when (it) {
                    is UIState.Error -> {
                        Log.e("more-error", it.error)
                    }
                    is UIState.Loading -> {
                        Log.e("more-error", "loading")
                    }
                    is UIState.Success -> {
                        it.data.forEach { student ->
                            when {
                                password.isEmpty() || username.isEmpty() -> {
                                    inputUsername.error =
                                        getString(R.string.edittext_error_message)
                                    inputPassword.error =
                                        getString(R.string.edittext_error_message)
                                    dialogProgressbar?.dismiss()
                                }
                                username == student?.fullName && password == student.password -> {
                                    Log.e("anime", "studentId: ${student.id}")
                                    notAdmin()
                                    preferences.userId = student.id
                                    Log.e("anime", "userId: ${preferences.userId}")
                                    Toast.makeText(requireContext(),
                                        "${preferences.userId}",
                                        Toast.LENGTH_LONG).show()
                                    dialogProgressbar?.dismiss()
                                    mainNavController().navigate(R.id.action_signInFragment_to_mainFlowFragment)
                                    wasOpen()
                                }
                                else -> {
                                    dialogProgressbar?.dismiss()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun wasOpen() {
        preferences.isOpenSignUp = false
    }

    private fun isAdmin() {
        preferences.isAdmin = true
    }

    private fun notAdmin() {
        preferences.isAdmin = false
    }

    private fun showProgressbar() {
        dialogProgressbar = showDialog(R.layout.dialog_progressbar)
        dialogProgressbar?.show()
    }

    private fun setLocale(locale: Localization) {
        if (preferences.getLanguageCode() != locale.languageCode) {
            preferences.setLocale(locale)
            activity?.recreate()
        }
    }
}
