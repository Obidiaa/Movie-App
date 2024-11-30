package com.obidia.movieapp.presentation.home

import ItemTrendingFilm
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.obidia.movieapp.R
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.presentation.category.CategoryDialog
import com.obidia.movieapp.presentation.component.HomeScreenRoute
import com.obidia.movieapp.presentation.component.Route
import com.obidia.movieapp.presentation.component.shimmerEffect
import kotlinx.coroutines.delay

fun NavGraphBuilder.homeScreenRoute(navigate: (Route) -> Unit) {
    composable<HomeScreenRoute> {
        val viewModel: HomeViewModel = hiltViewModel()

        HomeScreen(
            viewModel.filmState.collectAsStateWithLifecycle(),
            viewModel.listCategory.collectAsStateWithLifecycle(),
            viewModel.uiState.collectAsStateWithLifecycle(),
            viewModel::homeAction,
        )
    }
}

@Composable
fun HomeScreen(
    filmUiState: State<FilmUiState>,
    listCategory: State<Resource<List<CategoryModel>>?>,
    uiState: State<HomeUiState>,
    action: (HomeAction) -> Unit
) {

    val lazyListState = rememberLazyListState()

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

    LaunchedEffect(
        key1 = uiState.value.categoryName,
    ) {
        if (uiState.value.categoryName != "Categories" && uiState.value.categoryName != null || !uiState.value.isMovie || !uiState.value.isTvShow) {
            action(HomeAction.ContentVisible(false))
            delay(500)
            action(HomeAction.ContentVisible(true))
            lazyListState.scrollToItem(0)
            action(HomeAction.OnLoadFilmByCategory)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
            listTop10Tv = filmUiState.value.listTop10Tv.collectAsStateWithLifecycle()
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
            }
        )
    }

    CategoryDialog(
        listCategory = listCategory,
        isShowDialog = uiState.value.isShowCategoryDialog,
        onClickCategory = { genre, id ->
            action(HomeAction.OnClickCategoryDialog(genre, id.toString()))
        }
    ) {
        action(HomeAction.OnDismissCategoryDialog)
    }
}

@Composable
fun TopAppBar(
    isFirstItemVisible: Boolean,
    isTopBarVisible: Boolean,
    isTvShow: Boolean,
    isMovie: Boolean,
    category: String?,
    onClickClose: () -> Unit,
    onClickCategory: () -> Unit,
    onClickMovie: () -> Unit,
    onClickTvShow: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isFirstItemVisible) Color.Transparent else MaterialTheme.colorScheme.secondary.copy(
            alpha = 0.8f
        ),
        label = "",
        animationSpec = tween(500, easing = FastOutLinearInEasing)
    )

    Column(
        modifier = Modifier.drawBehind {
            drawRect(animatedColor)
        }
    ) {
        Spacer(
            modifier = Modifier.height(
                WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
        )
        TopBar()
        CategoryView(
            isTopBarVisible,
            isTvShow,
            isMovie,
            category,
            onClickClose,
            onClickCategory,
            onClickMovie,
            onClickTvShow
        )
    }
}

@Composable
fun CategoryView(
    isTopBarVisible: Boolean,
    isTvShow: Boolean,
    isMovie: Boolean,
    category: String?,
    onClickClose: () -> Unit,
    onClickCategory: () -> Unit,
    onClickMovie: () -> Unit,
    onClickTvShow: () -> Unit
) {
    AnimatedVisibility(
        visible = isTopBarVisible,
        enter = expandVertically(animationSpec = tween(300)),
        exit = shrinkVertically(animationSpec = tween(300))
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isMovie || !isTvShow) item {
                Icon(
                    modifier = Modifier
                        .border(
                            border = BorderStroke(
                                1.dp,
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            shape = RoundedCornerShape(200.dp)
                        )
                        .padding(4.dp)
                        .clickable {
                            onClickClose.invoke()
                        }
                        .animateItem(
                            fadeInSpec = tween(400),
                            fadeOutSpec = tween(400),
                            placementSpec = tween(400)
                        ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            if (isTvShow) item {
                CategoryItem("TV Shows", modifier = Modifier.animateItem(
                    fadeInSpec = tween(400),
                    fadeOutSpec = tween(400),
                    placementSpec = tween(400)
                ),
                    oncClick = {
                        onClickTvShow.invoke()
                    }
                )
            }

            if (isMovie) item {
                CategoryItem("Movies", modifier = Modifier.animateItem(
                    fadeInSpec = tween(400),
                    fadeOutSpec = tween(400),
                    placementSpec = tween(400)
                ),
                    oncClick = {
                        onClickMovie.invoke()
                    }
                )
            }

            if (!isMovie || !isTvShow) item {
                CategoryItem(
                    title = category ?: "Categories",
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(400),
                        fadeOutSpec = tween(400),
                        placementSpec = tween(400)
                    ),
                    oncClick = {
                        onClickCategory.invoke()
                    }
                )
            }
        }
    }
}

@Composable
fun BoxTransition(
    isContentVisible: Boolean
) {
    val animatedColorBox by animateColorAsState(
        targetValue = if (!isContentVisible) MaterialTheme.colorScheme.inverseSurface else Color.Transparent,
        label = "",
        animationSpec = tween(500)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind { drawRect(animatedColorBox) }
    )
}

@Composable
fun Content(
    contentVisible: Boolean,
    lazyListState: LazyListState,
    isMovie: Boolean,
    isTvShow: Boolean,
    listMovieNowPlaying: LazyPagingItems<ItemModel>,
    listMoviePopular: LazyPagingItems<ItemModel>,
    listMovieTopRated: LazyPagingItems<ItemModel>,
    listTvTopAiring: LazyPagingItems<ItemModel>,
    listTvPopular: LazyPagingItems<ItemModel>,
    listTvTopRated: LazyPagingItems<ItemModel>,
    filmHeader: State<Resource<ItemModel>?>,
    listTop10Movie: State<Resource<List<ItemModel>>?>,
    listTop10Tv: State<Resource<List<ItemModel>>?>
) {
    AnimatedVisibility(
        visible = contentVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 500)
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(durationMillis = 500, easing = EaseInQuart)
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseSurface),
            state = lazyListState
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(
                        WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.height(112.dp))
            }

            item {
                HeaderMovieTrending(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillParentMaxHeight(0.6f),
                    filmHeader
                )
            }

            item {
                AnimatedVisibility(
                    visible = isMovie, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    MovieList(
                        title = "Movie Now Playing",
                        list = listMovieNowPlaying,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem(),
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isMovie, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    MovieList(
                        title = "Movie Popular",
                        list = listMoviePopular,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem(),
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isMovie, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    MovieList(
                        title = "Movie Top Rated",
                        list = listMovieTopRated,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem(),
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isMovie && isTvShow, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    FilmListTrending(
                        list = listTop10Movie,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem()
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isTvShow, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    MovieList(
                        title = "Tv Airing Today",
                        list = listTvTopAiring,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem(),
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isTvShow, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    MovieList(
                        title = "Tv Popular",
                        list = listTvPopular,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem(),
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isTvShow, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    MovieList(
                        title = "Tv TopRated",
                        list = listTvTopRated,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem(),
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isMovie && isTvShow, enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    FilmListTrending(
                        text = "TV Trending",
                        list = listTop10Tv,
                        modifier = Modifier
                            .fillParentMaxHeight(0.3f)
                            .animateItem()
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(
                        WindowInsets.systemBars.asPaddingValues(LocalDensity.current)
                            .calculateBottomPadding()
                    )
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )
            }
        }
    }
}

@Composable
fun FilmListTrending(
    text: String = "Movie Trending",
    modifier: Modifier, list: State<Resource<List<ItemModel>>?>
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 4.dp, top = 20.dp),
            text = text,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Spacer(modifier = Modifier.width(0.dp)) }
            when (val data = list.value) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    itemsIndexed(items = data.data) { number, item ->
                        ItemTrendingFilm(number = number + 1, model = item)
                    }
                }

                null -> {}
            }

            item { Spacer(modifier = Modifier.width(0.dp)) }
        }
    }
}

@Composable
fun HeaderMovieTrending(
    modifier: Modifier,
    filmHeader: State<Resource<ItemModel>?>
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp)
            .clip(shape = RoundedCornerShape(12.dp))
    ) {

        when (val data = filmHeader.value) {
            is Resource.Error -> {}
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect(12.dp)
                )
            }

            is Resource.Success -> {
                AsyncImage(
                    error = painterResource(id = R.drawable.img_broken),
                    placeholder = painterResource(id = R.drawable.img_loading),
                    model = "https://image.tmdb.org/t/p/w500/${data.data.image}",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceDim,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp)),
                    contentDescription = "image"
                )
            }

            null -> {}
        }
    }
}

@Composable
fun MovieList(
    title: String, list: LazyPagingItems<ItemModel>,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 4.dp, top = 20.dp),
            text = title,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item { Spacer(modifier = Modifier.width(0.dp)) }

            items(list) { movie -> MovieItem(movie) }

            list.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(count = 20) { MovieItemPlaceholder() }
                    }

                    loadState.append is LoadState.Loading -> {
                        items(count = 20) { MovieItemPlaceholder() }
                    }
                }
            }

            item { Spacer(modifier = Modifier.width(0.dp)) }
        }
    }
}

@Composable
fun TopBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                text = "For Obid",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier.padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSecondary,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark),
                    contentDescription = "bookmark"
                )

                Icon(
                    tint = MaterialTheme.colorScheme.onSecondary,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = "search"
                )
            }
        }
    }
}