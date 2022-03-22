package com.geektech.intellect_memort.di

import android.content.Context
import android.content.SharedPreferences
import com.geektech.intellect_memort.common.utils.LocaleHelper
import com.geektech.intellect_memort.data.local.sharedpreferences.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("geektech.preferences", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(preferences: SharedPreferences) = PreferencesHelper(preferences)

    @Provides
    fun provideLocaleHelper(preferencesHelper: PreferencesHelper) = LocaleHelper(preferencesHelper)
}