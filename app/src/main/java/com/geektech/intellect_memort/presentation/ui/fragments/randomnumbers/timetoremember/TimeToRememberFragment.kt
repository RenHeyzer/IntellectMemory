package com.geektech.intellect_memort.presentation.ui.fragments.randomnumbers.timetoremember

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memort.common.extension.hideKeyboard
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.common.extension.visible
import com.geektech.intellect_memort.databinding.FragmentTimeToRememberBinding


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
        etTime.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (etTime.text?.isNotEmpty() == true && etTime.text.toString().toInt() < 61) {
                    time = etTime.text.toString().toInt()
                    etTime.hideKeyboard()
                    setUpBtnStartListener()
                    binding.parentLayout.setBackgroundColor(Color.parseColor("#4446AD"))
                    binding.btnStartr.visible()
                }
            }
            false
        }
    }

    private fun setUpBtnStartListener() {
        binding.btnStartr.setOnSingleClickListener {
            findNavController().navigate(
                TimeToRememberFragmentDirections.actionTimeToRememberFragmentToGameRandomNumbersFragment(
                    args.quantityNumbers,
                    time
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}