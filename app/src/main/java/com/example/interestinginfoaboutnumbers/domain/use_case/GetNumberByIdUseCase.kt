package com.example.interestinginfoaboutnumbers.domain.use_case

import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetNumberByIdUseCase(
    private val repository: NumberRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Number>> = flow {

        try {
            val numberInfo = repository.getNumberById(id)
            emit(Resource.Success(numberInfo))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Could not get information from DB."))
        }
    }
}