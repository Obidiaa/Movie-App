package com.obidia.movieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.presentation.component.HomeScreenRoute
import com.obidia.movieapp.presentation.home.homeScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            SetNav(HomeScreenRoute)
        }
    }

    @Composable
    private fun SetNav(startDestination: Any) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = startDestination
        ) {
            homeScreenRoute(navigate = ::navigate)
        }
    }

    private fun navigate(route: Any) {
        navController.navigate(route)
    }
}