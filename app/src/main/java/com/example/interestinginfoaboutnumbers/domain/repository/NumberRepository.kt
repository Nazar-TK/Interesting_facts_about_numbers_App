package com.example.interestinginfoaboutnumbers.domain.repository

import com.example.interestinginfoaboutnumbers.domain.model.Number
import kotlinx.coroutines.flow.Flow

interface NumberRepository {

    suspend fun insertNumber(number: Number)
    suspend fun getNumberInfo(number: Int): String

    suspend fun getRandomNumberInfo(): String

    suspend fun getAllNumbers(): Flow<List<Number>>
}