package com.geektech.intellect_memort.data.local.sharedpreferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(private val preferences: SharedPreferences) {

    var isOpenSignUp: Boolean
        get() = preferences.getBoolean("signUp", true)
        set(value) = preferences.edit().putBoolean("signUp", value).apply()

    var userId: String?
        get() = preferences.getString("userId", "")
        set(value) = preferences.edit().putString("userId", value).apply()

}