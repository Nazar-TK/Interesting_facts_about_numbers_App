package com.example.interestinginfoaboutnumbers.domain.use_case

import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow

class DeleteNotesUseCase(
    private val repository: NumberRepository
    ) {
        suspend operator fun invoke(numbers: List<Number>) {
            repository.deleteNumbers(numbers)
        }
}