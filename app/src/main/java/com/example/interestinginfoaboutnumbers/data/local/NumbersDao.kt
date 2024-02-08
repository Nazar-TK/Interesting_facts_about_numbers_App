package com.example.interestinginfoaboutnumbers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.interestinginfoaboutnumbers.domain.model.Number
import kotlinx.coroutines.flow.Flow


@Dao
interface NumbersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(number: Number)

    @Query("SELECT * FROM number")
    fun getAllNumbersInfos(): Flow<List<Number>>

    @Query("SELECT * FROM number WHERE id = :id")
    suspend fun getNumberById(id: Int): Number?
}
