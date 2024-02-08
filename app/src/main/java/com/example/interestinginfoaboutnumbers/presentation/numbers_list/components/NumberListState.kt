package com.example.interestinginfoaboutnumbers.presentation.numbers_list.components

import com.example.interestinginfoaboutnumbers.domain.model.Number

data class NumberListState (
    val numberListItems : List<Number> = emptyList(),
)