package com.obidia.movieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.presentation.component.HomeScreenRoute
import com.obidia.movieapp.presentation.home.homeScreenRoute
import com.obidia.movieapp.presentation.search.searchScreenRout
import com.obidia.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            MovieAppTheme {
                SetNav(HomeScreenRoute)
            }
        }
    }

    @Composable
    private fun SetNav(startDestination: Any) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = startDestination
        ) {
            homeScreenRoute(navigate = ::navigate)
            searchScreenRout(navigate = ::navigate)
        }
    }

    private fun navigate(route: Any) {
        navController.navigate(route)
    }
}