package com.obidia.movieapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obidia.movieapp.presentation.util.shimmerEffect

@Composable
fun MovieItemPlaceholder() {
    AsyncImage(
        model = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .aspectRatio(9f / 16f)
            .fillMaxSize()
            .shimmerEffect(4.dp),
        contentDescription = "image"
    )
}

@Composable
fun MovieItemBlack() {
    AsyncImage(
        model = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .aspectRatio(9f / 16f)
            .fillMaxSize()
            .background(Color.Red),
        contentDescription = "image"
    )
}

@Preview
@Composable
fun PreviewMovieItemPlaceholder() {
    MovieItemPlaceholder()
}