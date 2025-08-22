package com.obidia.movieapp.presentation.home

import ItemTrendingFilm
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.obidia.movieapp.R
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.presentation.util.robotoFamily
import com.obidia.movieapp.presentation.util.shimmerEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
    listTop10Tv: State<Resource<List<ItemModel>>?>,
    action: (HomeAction) -> Unit,
    backgroundColor: Color?
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
                Column(
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                (backgroundColor?.copy(alpha = 0.4f)
                                    ?: MaterialTheme.colorScheme.inverseSurface),
                                MaterialTheme.colorScheme.inverseSurface
                            )
                        )
                    )
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(
                                WindowInsets.statusBars
                                    .asPaddingValues()
                                    .calculateTopPadding()
                            )
                    )
                    Spacer(
                        modifier = Modifier
                            .height(112.dp)
                    )
                    HeaderMovieTrending(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(0.6f),
                        filmHeader,
                        action = action
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
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Spacer(
                    modifier = Modifier.height(
                        WindowInsets.systemBars.asPaddingValues(LocalDensity.current)
                            .calculateBottomPadding()
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
            fontFamily = robotoFamily,
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
    filmHeader: State<Resource<ItemModel>?>,
    action: (HomeAction) -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp)
            .clip(shape = RoundedCornerShape(12.dp))
    ) {
        val inverseSurface = MaterialTheme.colorScheme.inverseSurface

        when (val data = filmHeader.value) {
            is Resource.Error -> {}
            is Resource.Loading -> {
                action(HomeAction.OnChangeBackgroundColor(inverseSurface))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect(12.dp)
                )
            }

            is Resource.Success -> {
                val imagePainter =
                    rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/${data.data.image}")

                LaunchedEffect(imagePainter) {
                    val bitmap =
                        (imagePainter.imageLoader.execute(imagePainter.request).drawable as BitmapDrawable).bitmap.toNonHardwareBitmap()
                    val palette = withContext(Dispatchers.Default) {
                        Palette.from(bitmap).generate()
                    }
                    palette.vibrantSwatch?.let { swatch ->
                        action(HomeAction.OnChangeBackgroundColor(Color(swatch.rgb)))
                    }
                }

                AsyncImage(
                    onLoading = {
                        action(HomeAction.OnChangeBackgroundColor(inverseSurface))
                    },
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
            fontFamily = robotoFamily,
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