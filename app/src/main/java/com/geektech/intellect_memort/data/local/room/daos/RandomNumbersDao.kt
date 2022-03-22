package com.geektech.intellect_memort.data.local.room.daos

import androidx.room.*
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomNumbersDao {

    @Query("SELECT * FROM random_numbers_model")
    fun getAllRandomNumbers(): Flow<List<RandomNumbersModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRandomNumbers(numbers: List<RandomNumbersModel>)

    @Delete
    suspend fun deleteNumber(number: RandomNumbersModel)

    @Query("DELETE FROM random_numbers_model")
    suspend fun deleteAllRandomNumbers()
}