package com.example.interestinginfoaboutnumbers.domain.use_case

import android.util.Log
import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import com.example.interestinginfoaboutnumbers.presentation.numbers_list.NumbersListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddNumberUseCase @Inject constructor(
    private val repository: NumberRepository
) {
    private val TAG: String? = AddNumberUseCase::class.simpleName
    operator fun invoke(number: Number): Flow<Resource<Any>> = flow {

        try {
            emit(Resource.Loading())
            repository.insertNumber(number)
            Log.d(TAG, "Inserted successfully!")
            emit(Resource.Success(number))
        } catch (e: Exception) {
            Log.d(TAG, "Not inserted")
            emit(Resource.Error(e.localizedMessage ?: "Unable to save the number."))
        }
    }
}