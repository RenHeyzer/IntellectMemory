package com.geektech.intellect_memort.presentation.ui.fragments.randomnumbers

import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.hideKeyboard
import com.geektech.intellect_memort.databinding.FragmentRandomNumbersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomNumbersFragment : BaseFragment<FragmentRandomNumbersBinding, RandomNumbersViewModel>(
    R.layout.fragment_random_numbers
) {
    override val binding by viewBinding(FragmentRandomNumbersBinding::bind)
    override val viewModel: RandomNumbersViewModel by viewModels()
    private var quantityNumbers: Int = 0

    override fun setupListeners() = with(binding) {
        etQuantityNumbers.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (etQuantityNumbers.text?.isNotEmpty() == true && etQuantityNumbers.text.toString()
                        .toInt() < 1001 && etQuantityNumbers.text.toString().toInt() > 0
                ) {
                    quantityNumbers = etQuantityNumbers.text.toString().toInt()
                    etQuantityNumbers.text?.clear()
                    etQuantityNumbers.hideKeyboard()
                    Log.e("anime", " QuantityNumbers $quantityNumbers")
                    findNavController().navigate(
                        RandomNumbersFragmentDirections.actionRandomNumbersFragmentToTimeToRememberFragment(
                            quantityNumbers
                        )
                    )
                } else {
//                    etQuantityNumbers.error = ,
                }
            }
            false
        }

    }

}