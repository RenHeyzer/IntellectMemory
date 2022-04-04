package com.geektech.intellect_memort.common.extension

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.geektech.intellect_memort.R

fun Fragment.signNavController() =
    requireActivity().findNavController(R.id.nav_host_fragment_sign_container)

fun Fragment.mainNavController() =
    requireActivity().findNavController(R.id.nav_host_fragment_main_container)

fun Fragment.navNavController() =
    requireActivity().findNavController(R.id.fragmentContainerView)