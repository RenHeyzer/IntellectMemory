package com.geektech.intellect_memory.presentation.ui.fragments.randomnumbers.timetoremember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.extension.hideKeyboard
import com.geektech.intellect_memory.common.extension.setOnSingleClickListener
import com.geektech.intellect_memory.databinding.FragmentTimeToRememberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeToRememberFragment : Fragment() {
    private var _binding: FragmentTimeToRememberBinding? = null
    private val binding get() = _binding!!
    private val args: TimeToRememberFragmentArgs by navArgs()
    private var time: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTimeToRememberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() = with(binding) {
        binding.btnStart.setOnSingleClickListener {
            when {
                etTime.text.toString().toInt() > 61 -> {
                    etTime.error = getString(R.string.error_text_input_admissible_time)
                }
                etTime.text.toString().toInt() <= 0 -> {
                    etTime.error = getString(R.string.error_text_input_admissible_time)
                }
                etTime.text.length > 2 -> {
                    etTime.error = getString(R.string.error_text_input_admissible_time)
                }
                etTime.text == null || etTime.text.toString() == " " -> {
                    etTime.error = getString(R.string.error_text_input_admissible_time)
                }
                else -> {
                    time = etTime.text.toString().toInt()
                    etTime.hideKeyboard()
                    setUpBtnStartListener()
                }
            }
        }
        setupBtnBackClickListener()
    }

    private fun setupBtnBackClickListener() {
        binding.btnBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpBtnStartListener() {
            findNavController().navigate(
                TimeToRememberFragmentDirections.actionTimeToRememberFragmentToGameRandomNumbersFragment(
                    isBinary = args.isBinary,
                    quantitynumber = args.quantityNumbers,
                    time = time
                )
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}