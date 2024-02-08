package com.example.interestinginfoaboutnumbers.domain.use_case

import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddNumberUseCase @Inject constructor(
    private val repository: NumberRepository
) {

    operator fun invoke(number: Number): Flow<Resource<Any>> = flow {

        try {
            emit(Resource.Loading())
            repository.insertNumber(number)
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unable to save the number."))
        }
    }
}