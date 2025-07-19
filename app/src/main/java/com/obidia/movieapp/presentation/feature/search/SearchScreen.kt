package com.obidia.movieapp.presentation.feature.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.feature.home.MovieItemPlaceholder
import com.obidia.movieapp.presentation.util.MovieItem
import com.obidia.movieapp.presentation.util.SearchScreenRoute
import com.obidia.movieapp.presentation.util.StatusBarSpace
import com.obidia.movieapp.presentation.util.TopBar

fun NavGraphBuilder.searchScreenRoute(navigateToDetail: (Int, NavBackStackEntry) -> Unit) {
    composable<SearchScreenRoute> {
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreen(
            viewModel.uiState.collectAsStateWithLifecycle(),
            viewModel::searchEvents,
            navigateToDetail,
            it
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    uiStat: State<SearchUiStat>,
    action: (SearchEvents) -> Unit,
    navigateToDetail: (Int, NavBackStackEntry) -> Unit,
    navBackStackEntry: NavBackStackEntry
) {
    val list = uiStat.value.listSearch.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StatusBarSpace()

        TopBar(title = "Search")

        BasicTextField(
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            value = uiStat.value.textSearch,
            onValueChange = {
                action.invoke(SearchEvents.OnAddTextSearch(it))
            },
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    value = uiStat.value.textSearch,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Search Movie",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        if (uiStat.value.textSearch.isNotEmpty()) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .clickable {
                                        action(SearchEvents.OnClickClose)
                                    },
                                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                                contentDescription = ""
                            )
                        }
                    },
                )
            }
        )

        if (uiStat.value.textSearch.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(list.itemCount) {
                    MovieItem(
                        item = list[it], onClick = {
                            navigateToDetail(list[it]?.id ?: 0, navBackStackEntry)
                        }
                    )
                }

                list.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            items(count = 50) { MovieItemPlaceholder() }
                        }

                        loadState.append is LoadState.Loading -> {
                            items(count = 50) { MovieItemPlaceholder() }
                        }

                        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                            Log.d(
                                "this it error",
                                (loadState.refresh as LoadState.Error).error.message ?: ""
                            )
                        }
                    }
                }

                List(3) {
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}