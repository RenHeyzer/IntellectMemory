package com.geektech.intellect_memory.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memory.R
import com.geektech.intellect_memory.common.utils.LocaleHelper
import com.geektech.intellect_memory.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memory.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private val binding by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var preferences: PreferencesHelper

    @Inject
    lateinit var localeHelper: LocaleHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Intellect_memort)
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().signInAnonymously()
        localeHelper.loadLocale(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        when (preferences.isOpenSignUp) {
            true -> {
                navGraph.setStartDestination(R.id.signFlowFragment)
            }
            false -> {
                navGraph.setStartDestination(R.id.mainFlowFragment)
            }
        }
        navController.graph = navGraph
    }
}