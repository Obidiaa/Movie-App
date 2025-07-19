package com.obidia.movieapp.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.obidia.movieapp.presentation.feature.bookmark.bookMarkScreenRoute
import com.obidia.movieapp.presentation.feature.detail.detailScreenRoute
import com.obidia.movieapp.presentation.feature.home.homeScreenRoute
import com.obidia.movieapp.presentation.feature.search.searchScreenRoute
import com.obidia.movieapp.presentation.util.BookMarkScreenRoute
import com.obidia.movieapp.presentation.util.HomeScreenRoute
import com.obidia.movieapp.presentation.util.MainScreen
import com.obidia.movieapp.presentation.util.Route
import com.obidia.movieapp.presentation.util.SearchScreenRoute
import com.obidia.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    private val navigationBarItems = listOf(
        NavigationBarItem("Home", Icons.Filled.Home, HomeScreenRoute),
        NavigationBarItem("Search", Icons.Filled.Search, SearchScreenRoute),
        NavigationBarItem("Bookmark", Icons.Filled.Favorite, BookMarkScreenRoute)
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) {
                true
            }
        )
        setContent {
            navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            MovieAppTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {

                        if (currentRoute?.contains(
                                HomeScreenRoute::class.simpleName ?: ""
                            ) == true ||
                            currentRoute?.contains(
                                SearchScreenRoute::class.simpleName ?: ""
                            ) == true ||
                            currentRoute?.contains(
                                BookMarkScreenRoute::class.simpleName ?: ""
                            ) == true
                        ) Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                navigationBarItems.forEach { item ->
                                    Column(
                                        modifier = Modifier
                                            .width((LocalConfiguration.current.screenWidthDp / 3).dp)
                                            .clickable {
                                                navigate(item.route)
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Icon(
                                            tint = if (currentRoute.contains(item.route.toString())) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.outlineVariant,
                                            imageVector = item.icon,
                                            contentDescription = ""
                                        )
                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontSize = 10.sp,
                                                color = if (currentRoute.contains(item.route.toString())) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.outlineVariant
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                }
                            }

                            Spacer(
                                modifier = Modifier.height(
                                    WindowInsets.systemBars.asPaddingValues(LocalDensity.current)
                                        .calculateBottomPadding()
                                )
                            )
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier.padding(
                            start = it.calculateStartPadding(LayoutDirection.Rtl),
                            end = it.calculateStartPadding(LayoutDirection.Ltr),
                            bottom = if (currentRoute?.contains(
                                    HomeScreenRoute::class.simpleName ?: ""
                                ) == true ||
                                currentRoute?.contains(
                                    SearchScreenRoute::class.simpleName ?: ""
                                ) == true ||
                                currentRoute?.contains(
                                    BookMarkScreenRoute::class.simpleName ?: ""
                                ) == true
                            ) it.calculateBottomPadding() else 0.dp
                        )
                    ) {
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
            detailScreenRoute()
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
        val icon: ImageVector,
        val route: Route
    )
}