package com.geektech.intellect_memory.presentation.ui.fragments.randomnumbers

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.base.BaseFragment
import com.geektech.intellect_memory.common.extension.hideKeyboard
import com.geektech.intellect_memory.common.extension.setOnSingleClickListener
import com.geektech.intellect_memory.databinding.FragmentInputCountOfNumbersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputCountOfNumbersFragment :
    BaseFragment<FragmentInputCountOfNumbersBinding, InputCountOfNumbersViewModel>(
        R.layout.fragment_input_count_of_numbers
    ) {
    override val binding by viewBinding(FragmentInputCountOfNumbersBinding::bind)
    override val viewModel: InputCountOfNumbersViewModel by viewModels()
    private val args by navArgs<InputCountOfNumbersFragmentArgs>()
    private var quantityNumbers: Int = 0

    override fun setupListeners() = with(binding) {
        btnFurther.setOnSingleClickListener {
            if (etQuantityNumbers.text?.isNotEmpty() == true && etQuantityNumbers.text.toString()
                    .toInt() < 1001 && etQuantityNumbers.text.toString()
                    .toInt() > 0 && etQuantityNumbers.text[0].code != 0
            ) {
                quantityNumbers = etQuantityNumbers.text.toString().toInt()
                etQuantityNumbers.text?.clear()
                etQuantityNumbers.hideKeyboard()
                Log.e("anime", " QuantityNumbers $quantityNumbers")
                findNavController().navigate(
                    InputCountOfNumbersFragmentDirections.actionRandomNumbersFragmentToTimeToRememberFragment(
                        quantityNumbers,
                        args.isBinary
                    )
                )
            } else {
                etQuantityNumbers.error = getString(R.string.error_text_input_admissible_number)
            }
        }
        setupBackListener()
    }

    private fun setupBackListener() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}