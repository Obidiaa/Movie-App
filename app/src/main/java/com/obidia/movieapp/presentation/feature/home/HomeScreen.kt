package com.obidia.movieapp.presentation.feature.home

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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.presentation.util.HomeScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeScreenRoute(navigateToDetail: (Int, NavBackStackEntry) -> Unit) {
    composable<HomeScreenRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        val action = viewModel::homeAction

        HomeScreen(
            viewModel.filmState.collectAsStateWithLifecycle(),
            viewModel.listCategory.collectAsStateWithLifecycle(),
            viewModel.uiState.collectAsStateWithLifecycle(),
            action,
            Modifier.fillMaxSize(),
            it,
            navigateToDetail
        )
    }
}

@Composable
fun HomeScreen(
    filmUiState: State<FilmUiState>,
    listCategory: State<Resource<List<CategoryModel>>?>,
    uiState: State<HomeUiState>,
    action: (HomeAction) -> Unit,
    modifier: Modifier,
    navBackStackEntry: NavBackStackEntry,
    navigateToDetail: (Int, NavBackStackEntry) -> Unit
) {

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current.density

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

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val offset = uiState.value.categoryOffset + (available.y / density / 2).dp
                action(HomeAction.SetCategoryOffset(offset.coerceIn((-56).dp, 0.dp)))
                return Offset(0f, 0f)
            }
        }
    }

    Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
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
            navBackStackEntry = navBackStackEntry,
            navigateToDetail = navigateToDetail
        )

        BoxTransition(isContentVisible = uiState.value.isVisibleContent)

        TopAppBar(
            uiState.value.isFirstItemVisible,
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
            offset = uiState.value.categoryOffset,
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

    Box(
        modifier = Modifier
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