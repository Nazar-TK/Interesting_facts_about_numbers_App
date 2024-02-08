package com.example.interestinginfoaboutnumbers.domain.use_case

import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNumbersUseCase @Inject constructor(
    private val repository: NumberRepository
) {
    operator fun invoke(): Flow<Resource<List<Number>>> = flow {

        try {
            emit(Resource.Loading())
            val numbers = repository.getAllNumbers()
            val resultList = mutableListOf<Number>()
            numbers.collect { list ->
                resultList.addAll(list)
            }
            emit(Resource.Success(resultList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unable to retrieve numbers from DB."))
        }
    }
}