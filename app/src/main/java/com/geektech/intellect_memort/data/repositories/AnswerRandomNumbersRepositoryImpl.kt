package com.geektech.intellect_memort.data.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.data.local.room.daos.AnswerRandomNumbersDao
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import com.geektech.intellect_memort.domain.repositories.AnswerRandomNumbersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnswerRandomNumbersRepositoryImpl @Inject constructor(
    private val dao: AnswerRandomNumbersDao,
) : BaseRepository(), AnswerRandomNumbersRepository {

    override fun getAllAnswerOfNumbers() = dao.getAllAnswerOfNumbers()

    @WorkerThread
    override suspend fun insertAnswerOfNumber(number: AnswerNumbersModel) =
        withContext(Dispatchers.IO) {
            dao.insertAnswerOfNumber(number)
        }

    override suspend fun deleteAllAnswerOfNumbers() {
        dao.deleteAllAnswerOfNumbers()
    }

    override suspend fun deleteAnswerOfNumber(number: AnswerNumbersModel) {
        dao.deleteAnswerOfNumber(number)
    }
}