package com.obidia.movieapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obidia.movieapp.R
import com.obidia.movieapp.domain.model.ItemModel

@Composable
fun MovieItem(item: ItemModel?, modifier: Modifier = Modifier) {
    AsyncImage(
        error = painterResource(id = R.drawable.img_broken),
        placeholder = painterResource(id = R.drawable.img_loading),
        model = "https://image.tmdb.org/t/p/w500/${item?.image}",
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .aspectRatio(10f / 16f)
            .background(
                color = androidx.compose.ui.graphics.Color.Red,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp)),
        contentDescription = "image"
    )
}

@Preview
@Composable
fun PreviewMovieItem() {
    MovieItem(ItemModel(0, "", ""))
}