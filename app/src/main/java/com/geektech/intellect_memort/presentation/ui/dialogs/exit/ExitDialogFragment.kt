package com.geektech.intellect_memort.presentation.ui.dialogs.exit

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.extension.setOnSingleClickListener
import com.geektech.intellect_memort.databinding.FragmentExitDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExitDialogFragment : DialogFragment() {

    private var binding: FragmentExitDialogBinding? = null
    private val args by navArgs<ExitDialogFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentExitDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(activity)
            .setView(binding?.root)
            .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initialize()
        setupListeners()
        return dialog
    }

    private fun initialize() {
        binding?.apply {
            if (args.withResults) {
                dialogText.text = getString(R.string.exit_text)
            } else {
                dialogText.text = getString(R.string.exit_text_short)
            }
        }
    }

    private fun setupListeners() {
        setupDialogButtons()
    }

    private fun setupDialogButtons() {
        binding?.btnPositive?.setOnSingleClickListener {
            findNavController().navigate(
                ExitDialogFragmentDirections.actionExitDialogFragmentToHomeFragment()
            )
        }
        binding?.btnNegative?.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}