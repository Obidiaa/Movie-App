package com.obidia.movieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.bookmark.bookMarkScreenRoute
import com.obidia.movieapp.presentation.util.BookMarkScreenRoute
import com.obidia.movieapp.presentation.util.HomeScreenRoute
import com.obidia.movieapp.presentation.util.MainScreen
import com.obidia.movieapp.presentation.util.Route
import com.obidia.movieapp.presentation.util.SearchScreenRoute
import com.obidia.movieapp.presentation.home.homeScreenRoute
import com.obidia.movieapp.presentation.search.searchScreenRoute
import com.obidia.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    private val navigationBarItems = listOf(
        NavigationBarItem("Home", R.drawable.ic_home, HomeScreenRoute),
        NavigationBarItem("Search", R.drawable.ic_search, SearchScreenRoute),
        NavigationBarItem("Bookmark", R.drawable.ic_bookmark, BookMarkScreenRoute)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            MovieAppTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            navigationBarItems.forEach { item ->
                                NavigationBarItem(
                                    selected = currentRoute?.contains(item.route.toString())
                                        ?: false,
                                    onClick = {
                                        navigate(item.route)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(item.icon),
                                            contentDescription = ""
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        indicatorColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
                        SetNav(HomeScreenRoute)
                    }
                }
            }
        }
    }

    @Composable
    private fun SetNav(startDestination: Any, modifier: Modifier = Modifier) {
        NavHost(
            modifier = modifier,
            navController = navController as NavHostController,
            startDestination = startDestination
        ) {
            homeScreenRoute(::navigate)
            searchScreenRoute(::navigate)
            bookMarkScreenRoute(::navigate)
        }
    }

    private fun navigate(route: Any) {
        when (route) {
            is MainScreen -> {
                if (route is HomeScreenRoute) {
                    navController.popBackStack(route, false)
                    return
                }

                navController.navigate(route) {
                    popUpTo(HomeScreenRoute)
                    launchSingleTop = true
                }
            }

            else -> navController.navigate(route)
        }
    }

    data class NavigationBarItem(
        val title: String,
        val icon: Int,
        val route: Route
    )
}