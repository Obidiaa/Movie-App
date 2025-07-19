package com.obidia.movieapp.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obidia.movieapp.domain.model.ItemModel

@Composable
fun MovieItem(item: ItemModel?, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    BaseCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        BaseImage(
            model = "https://image.tmdb.org/t/p/w500/${item?.image}",
            modifier = Modifier
                .aspectRatio(10f / 16f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(4.dp)
                ).clickable {
                    onClick()
                },
            contentDescription = "image"
        )
    }
}

@Preview
@Composable
fun PreviewMovieItem() {
    MovieItem(ItemModel(0, "", ""))
}