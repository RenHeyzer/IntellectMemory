package com.geektech.intellect_memory.data.repositories

import androidx.annotation.WorkerThread
import com.geektech.intellect_memory.common.base.BaseRepository
import com.geektech.intellect_memory.data.local.room.daos.AnswerNumbersDao
import com.geektech.intellect_memory.domain.models.AnswerNumbersModel
import com.geektech.intellect_memory.domain.repositories.AnswerRandomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnswerNumbersRepositoryImpl @Inject constructor(
    private val dao: AnswerNumbersDao,
) : BaseRepository(), AnswerRandomRepository {

    override fun getAllAnswerOfNumbers() = dao.getAllAnswerOfNumbers()

    @WorkerThread
    override suspend fun insertAllAnswerOfNumbers(numbers: List<AnswerNumbersModel>) =
        withContext(Dispatchers.IO) {
            dao.insertAllAnswerOfNumbers(numbers)
        }

    override suspend fun deleteAllAnswerOfNumbers() {
        dao.deleteAllAnswerOfNumbers()
    }
}