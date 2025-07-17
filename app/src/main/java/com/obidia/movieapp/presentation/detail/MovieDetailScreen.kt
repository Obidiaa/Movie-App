package com.obidia.movieapp.presentation.detail

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.home.toNonHardwareBitmap
import com.obidia.movieapp.presentation.util.DetailScreenRoute
import com.obidia.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

fun NavGraphBuilder.detailScreenRoute() {
    composable<DetailScreenRoute> {
        val viewModel = hiltViewModel<MovieDetailViewModel>()
        DetailScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle(),
            action = viewModel::action
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun DetailScreen(
    uiState: State<MovieDetailUiState>,
    action: (MovieDetailAction) -> Unit
) {
    val data = uiState.value.movieDetail

    var imagePainter: AsyncImagePainter? = null

    data?.backdropPath?.let {
        imagePainter = rememberAsyncImagePainter(data.backdropPath)
    }

    LaunchedEffect(imagePainter) {
        imagePainter?.let {
            if (data?.backdropPath?.isEmpty() == true) return@LaunchedEffect
            val bitmap =
                (it.imageLoader.execute(it.request).drawable as? BitmapDrawable)?.bitmap?.toNonHardwareBitmap()
                    ?: return@LaunchedEffect
            val palette = withContext(Dispatchers.Default) {
                bitmap.let { bitmap ->
                    Palette.from(bitmap).generate()
                }
            }
            palette.vibrantSwatch?.let { swatch ->
                action(MovieDetailAction.OnChangeTopBarColor(Color(swatch.rgb).copy(alpha = 0.4f)))
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            0.8f to (uiState.value.topBarColor?.copy(alpha = 0.2f)
                                ?: MaterialTheme.colorScheme.background),
                            1f to MaterialTheme.colorScheme.background,
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

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(56.dp)
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(start = 32.dp)
                            .size(32.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                        contentDescription = ""
                    )

                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(end = 32.dp)
                            .size(32.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark),
                        contentDescription = ""
                    )
                }

                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .aspectRatio(16 / 9f)
                ) {
                    AsyncImage(
                        error = painterResource(id = R.drawable.img_broken),
                        placeholder = painterResource(id = R.drawable.img_loading),
                        model = data?.backdropPath,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(0.3f)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!data?.posterPath.isNullOrEmpty()) {
                        AsyncImage(
                            model = data.posterPath, // Construct full URL
                            contentDescription = "Movie Poster",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxHeight()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp)
                                .weight(0.4f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Poster", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxHeight()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = data?.title ?: "",
                            maxLines = 2,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        if (data?.title != data?.originalTitle) {
                            Text(
                                text = "(${data?.originalTitle})",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Rating Star",
                                tint = Color(0xFFFFC107), // A nice gold color
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = String.format("%.1f/10", data?.voteAverage),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }

                        Text(
                            text = "Release Date: ${data?.releaseDate}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Text(
                            text = "Runtime: ${data?.runtime} minutes",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Text(
                            text = "Vote Average: ${data?.voteAverage}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Text(
                            text = "Revenue: ${data?.revenue}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    itemsIndexed(items = data?.genres ?: listOf()) { _, item ->
                        Row {
                            Box(
                                modifier = Modifier.background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(100.dp)
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(8.dp),
                                    text = item,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = data?.overview ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Movie More Info",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.height(8.dp))

                InfoRow("Original Language", data?.originalLanguage?.uppercase() ?: "")
                InfoRow("Country", data?.originalCountry ?: "")
                InfoRow("Vote", "${data?.voteAverage} (${data?.voteCount} votes)")
                InfoRow("Budget", "$${data?.budget}")
                InfoRow("IMDB ID", data?.imdbId ?: "")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "You Might Also Like",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                )
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            LazyRow(
                modifier = Modifier.fillParentMaxHeight(0.3f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.width(4.dp))
                }

                items(count = 10) {
                    Image(
                        painter = painterResource(id = R.drawable.img_loading),
                        modifier = Modifier
                            .aspectRatio(10f / 16f)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clip(RoundedCornerShape(4.dp)),
                        contentDescription = "image"
                    )
                }

                item {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun PreviewDetailScreen() {
    MovieAppTheme {
        DetailScreen(
            uiState = MutableStateFlow(MovieDetailUiState().mock()).collectAsStateWithLifecycle(),
            action = {})
    }
}