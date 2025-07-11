package com.obidia.movieapp.presentation.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.presentation.util.HomeScreenRoute
import com.obidia.movieapp.presentation.util.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeScreenRoute(navigate: (Route) -> Unit) {
    composable<HomeScreenRoute> {
        val viewModel: HomeViewModel = hiltViewModel()

        HomeScreen(
            viewModel.filmState.collectAsStateWithLifecycle(),
            viewModel.listCategory.collectAsStateWithLifecycle(),
            viewModel.uiState.collectAsStateWithLifecycle(),
            viewModel::homeAction,
            navigate,
            Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HomeScreen(
    filmUiState: State<FilmUiState>,
    listCategory: State<Resource<List<CategoryModel>>?>,
    uiState: State<HomeUiState>,
    action: (HomeAction) -> Unit,
    navigate: (Route) -> Unit,
    modifier: Modifier
) {

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemScrollOffset
        }.collect {
            action(
                HomeAction.TopAppBarState(
                    isTopBarVisible = !lazyListState.canScrollBackward || lazyListState.lastScrolledBackward,
                    isFirstItemVisible = lazyListState.firstVisibleItemIndex == 0 && it <= 200
                )
            )
        }
    }

    Box(modifier = modifier) {
        Content(
            contentVisible = uiState.value.isVisibleContent,
            isMovie = uiState.value.isMovie,
            isTvShow = uiState.value.isTvShow,
            lazyListState = lazyListState,
            listMovieNowPlaying = filmUiState.value.listMoviesNowPlaying.collectAsLazyPagingItems(),
            listMoviePopular = filmUiState.value.listMoviesPopular.collectAsLazyPagingItems(),
            listMovieTopRated = filmUiState.value.listMovieTopRated.collectAsLazyPagingItems(),
            listTvTopAiring = filmUiState.value.listTvAiringToday.collectAsLazyPagingItems(),
            listTvPopular = filmUiState.value.listTvPopular.collectAsLazyPagingItems(),
            listTvTopRated = filmUiState.value.listTvTopRated.collectAsLazyPagingItems(),
            filmHeader = filmUiState.value.filmHeader.collectAsStateWithLifecycle(),
            listTop10Movie = filmUiState.value.listTop10Movie.collectAsStateWithLifecycle(),
            listTop10Tv = filmUiState.value.listTop10Tv.collectAsStateWithLifecycle(),
            action = action,
            backgroundColor = uiState.value.backgroundColor,
            navigate = navigate
        )

        BoxTransition(isContentVisible = uiState.value.isVisibleContent)

        TopAppBar(
            uiState.value.isFirstItemVisible,
            uiState.value.isTopBarVisible,
            uiState.value.isTvShow,
            uiState.value.isMovie,
            uiState.value.categoryName,
            onClickClose = {
                action(HomeAction.OnClickClose)
            },
            onClickCategory = {
                action(HomeAction.OnClickCategory)
            },
            onClickMovie = {
                action(HomeAction.OnClickMovie)
            },
            onClickTvShow = {
                action(HomeAction.OnClickTvShow)
            },
            navigate = navigate
        )
    }

    CategoryDialog(
        listCategory = listCategory,
        isShowDialog = uiState.value.isShowCategoryDialog,
        onClickCategory = { genre, id ->
            action(HomeAction.OnClickCategoryDialog(genre, id.toString()))

            scope.launch {
                if (uiState.value.categoryName != "Categories" &&
                    uiState.value.categoryName != null
                ) {
                    action(HomeAction.ContentVisible(false))
                    delay(500)
                    action(HomeAction.ContentVisible(true))
                    lazyListState.scrollToItem(0)
                    action(HomeAction.OnLoadFilmByCategory)
                }
            }
        }
    ) {
        action(HomeAction.OnDismissCategoryDialog)
    }
}

@Composable
fun BoxTransition(
    isContentVisible: Boolean
) {
    val animatedColorBox by animateColorAsState(
        targetValue = if (!isContentVisible) MaterialTheme.colorScheme.background else Color.Transparent,
        label = "",
        animationSpec = tween(500)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind { drawRect(animatedColorBox) }
    )
}

@SuppressLint("NewApi")
fun Bitmap.toNonHardwareBitmap(): Bitmap {
    return if (this.config == Bitmap.Config.HARDWARE) {
        this.copy(Bitmap.Config.ARGB_8888, false)
    } else {
        this
    }
}