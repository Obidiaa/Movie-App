package com.obidia.movieapp.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // For loading images from URLs

// Assume you have a placeholder image in your drawables for now
// In a real app, you'd load the actual poster image from the URL.
// For this example, I'll use a placeholder.
// You'll need to add a dependency for Coil for AsyncImage:
// implementation("io.coil-kt:coil-compose:2.2.2") // Or the latest version

// Data class representing the movie information from the API
data class Movie(
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val genres: List<String>,
    val budget: Int,
    val revenue: Int,
    val posterPath: String?,
    val backdropPath: String?
)

// Dummy data for preview purposes, mimicking the API response
val dummyMovie = Movie(
    title = "The Endless Summer",
    originalTitle = "The Endless Summer",
    overview = "Bruce Brown's The Endless Summer is one of the first and most influential surf movies of all time. The film documents American surfers Mike Hynson and Robert August as they travel the world during California’s winter (which, back in 1965 was off-season for surfing) in search of the perfect wave and ultimately, an endless summer.",
    releaseDate = "1966-06-15",
    runtime = 91,
    voteAverage = 7.238,
    genres = listOf("Documentary"),
    budget = 50000,
    revenue = 0,
    posterPath = "/hinHiXl8sOGIz2TLIZNpO5rhjND.jpg", // This would be a full URL in a real app
    backdropPath = "/uNRfK14Ga8Hwfqt07vo8nvWQN1i.jpg" // This would be a full URL in a real app
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movie: Movie) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(movie.title) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Backdrop Image
            if (!movie.backdropPath.isNullOrEmpty()) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w780${movie.backdropPath}", // Construct full URL
                    contentDescription = "Movie Backdrop",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                // Placeholder if no backdrop path
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Backdrop Available", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Poster Image
                if (!movie.posterPath.isNullOrEmpty()) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w342${movie.posterPath}", // Construct full URL
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .padding(end = 16.dp)
                            .weight(0.4f)
                    )
                } else {
                    // Placeholder if no poster path
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .padding(end = 16.dp)
                            .weight(0.4f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Poster", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (movie.title != movie.originalTitle) {
                        Text(
                            text = "(${movie.originalTitle})",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating Star",
                            tint = Color(0xFFFFC107), // A nice gold color
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f/10", movie.voteAverage),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Release Date: ${movie.releaseDate}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Runtime: ${movie.runtime} minutes",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Genre(s): ${movie.genres.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Overview
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Additional Details (Budget, Revenue)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                if (movie.budget > 0) {
                    Text(
                        text = "Budget: $${String.format("%,d", movie.budget)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (movie.revenue > 0) {
                    Text(
                        text = "Revenue: $${String.format("%,d", movie.revenue)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieDetailScreen() {
    MaterialTheme { // Use MaterialTheme for preview
        MovieDetailScreen(movie = dummyMovie)
    }
}