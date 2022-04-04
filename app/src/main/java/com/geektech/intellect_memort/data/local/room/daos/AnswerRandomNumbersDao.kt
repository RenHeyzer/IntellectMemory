package com.geektech.intellect_memort.data.local.room.daos

import androidx.room.*
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerRandomNumbersDao {

    @Query("SELECT * FROM answer_numbers_model")
    fun getAllAnswerOfNumbers(): Flow<List<AnswerNumbersModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnswerOfNumber(numbers: AnswerNumbersModel)

    @Delete
    suspend fun deleteAnswerOfNumber(number: AnswerNumbersModel)

    @Query("DELETE FROM answer_numbers_model")
    suspend fun deleteAllAnswerOfNumbers()
}