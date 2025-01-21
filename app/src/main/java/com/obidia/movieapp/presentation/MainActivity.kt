package com.obidia.movieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.component.HomeScreenRoute
import com.obidia.movieapp.presentation.component.SearchScreenRoute
import com.obidia.movieapp.presentation.home.homeScreenRoute
import com.obidia.movieapp.presentation.search.searchScreenRout
import com.obidia.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    private val navigationBarItems = listOf(
        NavigationBarItem("Home", R.drawable.ic_home_filled),
        NavigationBarItem("Search", R.drawable.ic_search),
        NavigationBarItem("Bookmark", R.drawable.ic_bookmark)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            val selectedItem = remember { mutableIntStateOf(0) }
            MovieAppTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            navigationBarItems.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItem.intValue == index,
                                    onClick = {
                                        selectedItem.intValue = index
                                    }, icon = {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(item.icon),
                                            contentDescription = ""
                                        )
                                    }

                                )
                            }
                        }
                    }
                ) {
                    SetNav(
                        when (selectedItem.intValue) {
                            0 -> HomeScreenRoute
                            1 -> SearchScreenRoute
                            else -> HomeScreenRoute
                        },
                        modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                    )
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
            homeScreenRoute(navigate = ::navigate)
            searchScreenRout(navigate = ::navigate)
        }
    }

    private fun navigate(route: Any) {
        navController.navigate(route)
    }
}

data class NavigationBarItem(
    val title: String,
    val icon: Int,
)