package com.geektech.intellect_memort.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geektech.intellect_memort.data.local.room.daos.AnswerRandomNumbersDao
import com.geektech.intellect_memort.data.local.room.daos.RandomNumbersDao
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.models.RandomNumbersModel

@Database(entities = [RandomNumbersModel::class, AnswerNumbersModel::class],
    version = 5,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun randomNumbersDao(): RandomNumbersDao
    abstract fun answerRandomNumbersDao(): AnswerRandomNumbersDao
}