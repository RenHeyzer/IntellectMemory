package com.geektech.intellect_memort.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geektech.intellect_memort.domain.models.NumbersModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NumbersDao {

    @Query("SELECT * FROM numbers_model")
    fun getAllRandomNumbers(): Flow<List<NumbersModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRandomNumbers(numbers: List<NumbersModel>)

    @Query("DELETE FROM numbers_model")
    suspend fun deleteAllRandomNumbers()
}