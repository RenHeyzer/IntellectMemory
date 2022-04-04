package com.geektech.intellect_memort.data.local.sharedpreferences

import android.content.SharedPreferences
import com.geektech.intellect_memort.common.constants.Constants
import com.geektech.intellect_memort.common.utils.Localization
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

    var isAdmin: Boolean
        get() = preferences.getBoolean("admin", false)
        set(value) = preferences.edit().putBoolean("admin", value).apply()

    fun getLanguage() = preferences.getString(Constants.LANGUAGE, "ru")

    fun getLanguageCode() = preferences.getString(Constants.LANGUAGE_CODE, "ru-RU")

    fun setLocale(localization: Localization) {
        preferences.edit().apply {
            putString(Constants.LANGUAGE, localization.language).apply()
            putString(Constants.LANGUAGE_CODE, localization.languageCode).apply()
        }
    }
}