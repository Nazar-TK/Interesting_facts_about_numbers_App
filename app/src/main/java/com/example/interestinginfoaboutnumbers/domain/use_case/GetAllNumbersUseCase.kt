package com.example.interestinginfoaboutnumbers.domain.use_case

import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow

class GetAllNumbersUseCase(
    private val repository: NumberRepository
) {
    operator fun invoke(): Flow<List<Number>> {

        return repository.getAllNumbersFromDB()
    }
}