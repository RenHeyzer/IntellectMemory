package com.geektech.intellect_memory.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geektech.intellect_memory.data.local.room.daos.AnswerNumbersDao
import com.geektech.intellect_memory.data.local.room.daos.NumbersDao
import com.geektech.intellect_memory.domain.models.AnswerNumbersModel
import com.geektech.intellect_memory.domain.models.NumbersModel

@Database(entities = [NumbersModel::class, AnswerNumbersModel::class],
    version = 6,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun randomNumbersDao(): NumbersDao
    abstract fun answerRandomNumbersDao(): AnswerNumbersDao
}