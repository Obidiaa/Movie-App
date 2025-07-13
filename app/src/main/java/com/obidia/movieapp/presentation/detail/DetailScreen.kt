package com.obidia.movieapp.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.util.DetailScreenRoute
import com.obidia.movieapp.ui.theme.MovieAppTheme

fun NavGraphBuilder.detailScreenRoute() {
    composable<DetailScreenRoute> {
        DetailScreen()
    }
}

data class DetailMovieModel(
    val image: String,
    val title: String,
    val date: String,
    val isBookmark: Boolean,
    val listGenre: List<String>,
    val tagline: String,
    val description: String,
    val originalTitle: String,
    val runTime: String,
    val voteCount: Int,
    val voteAverage: Double,
    val listRecommendation: List<String>
)

@Composable
fun DetailScreen() {
    val data = DetailMovieModel(
        image = "",
        title = "Blade Runner 2049",
        date = "12-12-2017",
        isBookmark = true,
        listGenre = listOf("Action", "Sci-Fi", "Sport", "Shounen", "Thriller", "Mystery"),
        tagline = "“The Search for the Perfect Wave!”",
        description = "Bruce Brown's The Endless Summer is one of the first and most influential surf movies of all time. The film documents American surfers Mike Hynson and Robert August as they travel the world during California’s winter (which, back in 1965 was off-season for surfing) in search of the perfect wave and ultimately, an endless summer.",
        originalTitle = "Blade Runner 2049",
        runTime = "91 minutes",
        voteCount = 1200,
        voteAverage = 8.0,
        listRecommendation = listOf("Blade Runner", "Blade Runner 2049", "Blade Runner")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 32.dp)
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                    contentDescription = ""
                )

                Icon(
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark),
                    contentDescription = ""
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxHeight(0.6f)
                    .fillParentMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .padding(32.dp)
            ) {
                Image(
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.background.copy(
                            alpha = 0.6f
                        ), blendMode = BlendMode.Darken
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    imageVector = ImageVector.vectorResource(R.drawable.img_loading),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Column {
                        Text(
                            text = data.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = data.date,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }

        item {
            LazyHorizontalStaggeredGrid(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalItemSpacing = 8.dp,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.1f),
                rows = StaggeredGridCells.Fixed(2),
            ) {
                item { Spacer(Modifier.width(24.dp)) }
                item { Spacer(Modifier.width(24.dp)) }
                itemsIndexed(items = data.listGenre) { index, item ->
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

                        if (index in (data.listGenre.size - 2)..<data.listGenre.size) {
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = data.tagline,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 32.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = data.description,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 32.dp),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Justify,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column {
                Text(
                    text = "Original Title",
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 32.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = data.originalTitle,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 32.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Row(modifier = Modifier.fillParentMaxWidth()) {
                Column(
                    modifier = Modifier.fillParentMaxWidth(0.5f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Runtime",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data.runTime,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                }

                Column(
                    modifier = Modifier.fillParentMaxWidth(0.5f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Vote Count",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data.voteCount.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Vote Average",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_start),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = data.voteCount.toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            Text(
                modifier = Modifier.fillParentMaxWidth().padding(horizontal = 32.dp),
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
                    Spacer(modifier = Modifier.width(20.dp))
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
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun PreviewDetailScreen() {
    MovieAppTheme {
        DetailScreen()
    }
}