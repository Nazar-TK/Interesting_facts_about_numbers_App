package com.example.interestinginfoaboutnumbers.domain.use_case

data class NumberUseCases(
    val addNumberUseCase: AddNumberUseCase,
    val getNumberUseCase: GetNumberUseCase,
    val getRandomNumberUseCase: GetRandomNumberUseCase,
    val getAllNumbersUseCase: GetAllNumbersUseCase
)
