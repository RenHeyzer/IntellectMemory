package com.geektech.intellect_memory.presentation.ui.fragments.main

import android.os.Bundle
import android.view.View
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.base.BaseFlowFragment
import com.geektech.intellect_memory.data.local.sharedpreferences.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFlowFragment : BaseFlowFragment(
    R.layout.fragment_main_flow, R.id.nav_host_fragment_main_container
) {
    @Inject
    lateinit var preferences: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGraph()
    }

    private fun setupGraph() {
        val mainGraph = navController.navInflater.inflate(R.navigation.main_graph)
        val studentsGraph = navController.navInflater.inflate(R.navigation.students_graph)

        if (preferences.isAdmin) {
            navController.graph = studentsGraph
        } else {
            navController.graph = mainGraph
        }
    }
}