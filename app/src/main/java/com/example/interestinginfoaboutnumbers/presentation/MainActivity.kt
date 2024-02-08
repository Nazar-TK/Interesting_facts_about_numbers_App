package com.example.interestinginfoaboutnumbers.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interestinginfoaboutnumbers.presentation.number_info.NumberInfoScreen
import com.example.interestinginfoaboutnumbers.presentation.numbers_list.NumberListScreen
import com.example.interestinginfoaboutnumbers.presentation.ui.theme.InterestingInfoAboutNumbersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterestingInfoAboutNumbersTheme {

                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NumberListScreen.route
                    ) {
                        composable(
                            route = Screen.NumberListScreen.route
                        ) {
                            NumberListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.NumberInfoScreen.route + "/{numberId}"
                        ) {
                            NumberInfoScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
