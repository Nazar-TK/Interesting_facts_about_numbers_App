package com.example.interestinginfoaboutnumbers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interestinginfoaboutnumbers.domain.model.Number
@Database(
    entities = [Number::class],
    version = 1
)
abstract class NumberDatabase : RoomDatabase()
{
    abstract val dao: NumbersDao

    companion object {
        const val DATABASE_NAME = "numbers_db"
    }
}