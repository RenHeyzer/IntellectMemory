package com.geektech.intellect_memory.common.extension

import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.geektech.intellect_memory.R

fun Fragment.signNavController() =
    requireActivity().findNavController(R.id.nav_host_fragment_sign_container)

fun NavController.navigateSafely(@IdRes actionId: Int) {
    currentDestination?.getAction(actionId)?.let { navigate(actionId) }
}

fun NavController.navigateSafely(directions: NavDirections) {
    currentDestination?.getAction(directions.actionId)?.let { navigate(directions) }
}

fun Fragment.overrideOnBackPressed(onBackPressed: OnBackPressedCallback.() -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        onBackPressed()
    }
}

fun Fragment.mainNavController() =
    requireActivity().findNavController(R.id.nav_host_fragment_main_container)

fun Fragment.navNavController() =
    requireActivity().findNavController(R.id.fragmentContainerView)