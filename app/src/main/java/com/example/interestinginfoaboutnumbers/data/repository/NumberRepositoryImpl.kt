package com.example.interestinginfoaboutnumbers.data.repository

import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.data.local.NumbersDao
import com.example.interestinginfoaboutnumbers.data.remote.NumbersApi
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

class NumberRepositoryImpl @Inject constructor(
    private val api: NumbersApi,
    private val dao: NumbersDao
) : NumberRepository {
    override suspend fun insertNumber(number: Number) {
        dao.insertNumber(number)
    }

    override suspend fun getNumberInfo(number: Int): String {
        return api.getNumberInfo(number)
    }


    override suspend fun getRandomNumberInfo(): String {
        return api.getRandomNumberInfo()
    }

    override fun getAllNumbers(): Flow<List<Number>> {
        return dao.getAllNumbersInfos()
    }

    override suspend fun deleteNumbers(numbers: List<Number>) {
        dao.deleteNumbers(numbers = numbers)
    }
}