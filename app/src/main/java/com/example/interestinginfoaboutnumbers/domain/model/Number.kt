package com.example.interestinginfoaboutnumbers.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Number(
    @PrimaryKey val id: Int? = null,
    val number: Int,
    val info: String
)
