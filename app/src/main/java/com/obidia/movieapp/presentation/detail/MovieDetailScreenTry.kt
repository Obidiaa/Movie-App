package com.obidia.movieapp.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obidia.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenTry(movie: MovieTry) {
    val scrollState = rememberScrollState()
    val imageBaseUrl = "https://image.tmdb.org/t/p/w500"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Backdrop
            AsyncImage(
                model = imageBaseUrl + movie.backdropPath,
                contentDescription = "Backdrop",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Poster and title section
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = imageBaseUrl + movie.posterPath,
                    contentDescription = "Poster",
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(movie.title, style = MaterialTheme.typography.headlineSmall)
                    Text(movie.tagline ?: "", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = "${movie.releaseDate.take(4)} • ${movie.runtime} min",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = movie.genres.joinToString { it.name },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Overview
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Overview", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(movie.overview, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Info Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Movie Info", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                InfoRow("Original Language", movie.originalLanguage.uppercase())
                InfoRow("Country", movie.originCountry.joinToString())
                InfoRow("Vote", "${movie.voteAverage} (${movie.voteCount} votes)")
                InfoRow("Budget", "$${movie.budget}")
                InfoRow("IMDB ID", movie.imdbId)
            }

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
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.bodySmall)
    }
}

data class MovieTry(
    val title: String,
    val tagline: String?,
    val overview: String,
    val releaseDate: String,
    val runtime: Int,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originCountry: List<String>,
    val voteAverage: Double,
    val voteCount: Int,
    val budget: Int,
    val imdbId: String,
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    val sampleMovie = MovieTry(
        title = "The Endless Summer",
        tagline = "The Search for the Perfect Wave!",
        overview = "Bruce Brown's The Endless Summer is one of the first and most influential surf movies of all time. The film documents American surfers Mike Hynson and Robert August as they travel the world during California’s winter in search of the perfect wave and ultimately, an endless summer.",
        releaseDate = "1966-06-15",
        runtime = 91,
        posterPath = "/hinHiXl8sOGIz2TLIZNpO5rhjND.jpg",
        backdropPath = "/uNRfK14Ga8Hwfqt07vo8nvWQN1i.jpg",
        originalLanguage = "en",
        originCountry = listOf("US"),
        voteAverage = 7.238,
        voteCount = 123,
        budget = 50000,
        imdbId = "tt0060371",
        genres = listOf(Genre(99, "Documentary"))
    )

    MovieAppTheme  {
        MovieDetailScreenTry(movie = sampleMovie)
    }
}


