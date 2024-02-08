package com.example.interestinginfoaboutnumbers.domain.use_case

import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRandomNumberUseCase @Inject constructor(
    private val repository: NumberRepository
) {

    operator fun invoke(): Flow<Resource<String>> = flow {

        try {
            emit(Resource.Loading())
            val numberInfo = repository.getRandomNumberInfo()
            emit(Resource.Success(numberInfo))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Could not reach the server. Check your Internet connection."))
        }
    }
}