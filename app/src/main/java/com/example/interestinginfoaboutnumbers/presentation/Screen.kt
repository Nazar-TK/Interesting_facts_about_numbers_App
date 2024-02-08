package com.example.interestinginfoaboutnumbers.presentation

sealed class Screen(val route: String) {
    data object NumberListScreen: Screen("number_list_screen")
    data object NumberInfoScreen: Screen("number_info_screen")
}
