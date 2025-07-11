package com.obidia.movieapp.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.util.DetailScreenRoute
import com.obidia.movieapp.presentation.util.robotoFamily

fun NavGraphBuilder.detailScreenRoute() {
    composable<DetailScreenRoute> {
        DetailScreen()
    }
}

@Composable
fun DetailScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Image(
                modifier = Modifier.aspectRatio(16f / 9f),
                imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = "Blade Runner 2049",
                    fontFamily = robotoFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Text(
                    text = "Rate : 8/10",
                    fontFamily = robotoFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun PreviewDetailScreen() {
    DetailScreen()
}