package com.obidia.movieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.home.HomeScreen
import com.obidia.movieapp.presentation.search.SearchScreen
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
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ) {
                            navigationBarItems.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItem.intValue == index,
                                    onClick = {
                                        selectedItem.intValue = index
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
                    }) {
                    when (selectedItem.intValue) {
                        0 -> HomeScreen(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) { }
                        1 -> SearchScreen(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {}
                        2 -> SearchScreen(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {}
                    }
                }
            }
        }

//        @Composable
//        private fun SetNav(startDestination: Any, modifier: Modifier = Modifier) {
//            NavHost(
//                modifier = modifier,
//                navController = navController as NavHostController,
//                startDestination = startDestination
//            ) {
////            homeScreenRoute(navigate = ::navigate)
////            searchScreenRout(navigate = ::navigate)
//            }
//        }
//
//        private fun navigate(route: Any) {
//            navController.navigate(route)
//        }
    }

    data class NavigationBarItem(
        val title: String,
        val icon: Int,
    )
}