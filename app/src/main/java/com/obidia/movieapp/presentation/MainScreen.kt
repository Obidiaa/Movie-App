package com.obidia.movieapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.presentation.feature.bookmark.bookMarkScreenRoute
import com.obidia.movieapp.presentation.feature.home.homeScreenRoute
import com.obidia.movieapp.presentation.feature.search.searchScreenRoute
import com.obidia.movieapp.presentation.util.BookMarkScreenRoute
import com.obidia.movieapp.presentation.util.HomeScreenRoute
import com.obidia.movieapp.presentation.util.MainScreenRoute
import com.obidia.movieapp.presentation.util.SearchScreenRoute
import com.obidia.movieapp.presentation.util.SystemBarSpace

enum class MainSection(val title: String, val mainScreenRoute: MainScreenRoute, val icon: ImageVector) {
    HOME("Home", HomeScreenRoute, Icons.Filled.Home),
    SEARCH("Search", SearchScreenRoute, Icons.Filled.Search),
    BOOKMARK("Bookmark", BookMarkScreenRoute, Icons.Filled.Favorite),
}

fun NavGraphBuilder.mainScreenRoute(
    navigateToDetail: (Int, NavBackStackEntry) -> Unit,
) {
    composable<HomeScreenRoute> {
        MainScreen(navigateToDetail)
    }
}

@Composable
fun MainScreen(
    navigateToDetail: (Int, NavBackStackEntry) -> Unit,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun navigate(route: Any) {
        navController.navigate(route) {
            popUpTo(HomeScreenRoute)
            launchSingleTop = true
        }
    }

    Scaffold(
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceContainerLow),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MainSection.entries.toList().forEach { item ->
                        Column(
                            modifier = Modifier
                                .width((LocalConfiguration.current.screenWidthDp / 3).dp)
                                .clickable { navigate(item.mainScreenRoute) },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Icon(
                                tint = if (currentRoute?.contains(item.mainScreenRoute.toString()) == true)
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.outlineVariant,
                                imageVector = item.icon,
                                contentDescription = ""
                            )
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 10.sp,
                                    color = if (currentRoute?.contains(item.mainScreenRoute.toString()) == true)
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    else MaterialTheme.colorScheme.outlineVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                    }
                }

                SystemBarSpace()
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Rtl),
                end = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                bottom = innerPadding.calculateBottomPadding()
            ),
            navController = navController,
            startDestination = HomeScreenRoute
        ) {
            homeScreenRoute(navigateToDetail = navigateToDetail)
            searchScreenRoute(navigateToDetail = navigateToDetail)
            bookMarkScreenRoute(navigate = ::navigate)
        }
    }
}