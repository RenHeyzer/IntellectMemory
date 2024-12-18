package com.geektech.intellect_memory.di

import android.content.Context
import androidx.room.Room
import com.geektech.intellect_memory.data.local.room.AppDatabase
import com.geektech.intellect_memory.data.local.room.daos.AnswerNumbersDao
import com.geektech.intellect_memory.data.local.room.daos.NumbersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "intellect_memory_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideRandomNumbersDao(appDatabase: AppDatabase): NumbersDao =
        appDatabase.randomNumbersDao()

    @Provides
    fun provideAnswerRandomNumbersDao(appDatabase: AppDatabase): AnswerNumbersDao =
        appDatabase.answerRandomNumbersDao()
}