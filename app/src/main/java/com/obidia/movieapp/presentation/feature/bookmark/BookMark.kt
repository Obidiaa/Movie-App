package com.obidia.movieapp.presentation.feature.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.obidia.movieapp.presentation.util.BookMarkScreenRoute
import com.obidia.movieapp.presentation.util.MovieItem
import com.obidia.movieapp.presentation.util.StatusBarSpace
import com.obidia.movieapp.presentation.util.TopBar

fun NavGraphBuilder.bookMarkScreenRoute(navigateToDetail: (Int, NavBackStackEntry) -> Unit,) {
    composable<BookMarkScreenRoute> {
        val viewModel: MovieBookmarkViewModel = hiltViewModel()
        BookMarkScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle(),
            navBackStackEntry = it,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun BookMarkScreen(
    uiState: State<MovieBookmarkUiState>,
    navBackStackEntry: NavBackStackEntry,
    navigateToDetail: (Int, NavBackStackEntry) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StatusBarSpace()

        TopBar(title = "Bookmark")

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = uiState.value.listMovie) {
                MovieItem(
                    item = it,
                    onClick = {
                        navigateToDetail(it.id, navBackStackEntry)
                    }
                )
            }
        }
    }
}