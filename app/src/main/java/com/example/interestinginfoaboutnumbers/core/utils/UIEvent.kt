package com.example.interestinginfoaboutnumbers.core.utils

sealed class UIEvent {
    data class ShowSnackBar(
        val message: String,
    ) : UIEvent()
}