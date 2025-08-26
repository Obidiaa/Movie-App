package com.obidia.movieapp.presentation.util

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.feature.home.toNonHardwareBitmap
import com.obidia.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun StatusBarSpace() {
    Spacer(
        modifier = Modifier.height(
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        )
    )
}

@Composable
fun SystemBarSpace() {
    Spacer(
        modifier = Modifier.height(
            WindowInsets.systemBars.asPaddingValues(LocalDensity.current)
                .calculateBottomPadding()
        )
    )
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    iconStart: ImageVector? = null,
    iconEnd: ImageVector? = null,
    iconStartOnClick: (() -> Unit)? = null,
    iconEndOnClick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(56.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            iconStart?.let {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier.clickable {
                        iconStartOnClick?.invoke()
                    },
                    tint = MaterialTheme.colorScheme.onBackground,
                    imageVector = it,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                color = MaterialTheme.colorScheme.primaryContainer,
                text = title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            iconEnd?.let {
                Icon(
                    modifier = Modifier.clickable {
                        iconEndOnClick?.invoke()
                    },
                    tint = MaterialTheme.colorScheme.onBackground,
                    imageVector = it,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    MovieAppTheme {
        TopBar(
            title = "Jet Movie",
            iconStart = ImageVector.vectorResource(R.drawable.ic_arrow_back),
            iconEnd = ImageVector.vectorResource(R.drawable.ic_bookmark)
        )
    }
}

@Composable
fun BaseCard(
    shape: Shape,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = shape,
        content = { content() },
    )
}

@Composable
fun BaseImage(
    model: String,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.FillBounds
) {
    AsyncImage(
        error = painterResource(id = R.drawable.img_broken),
        placeholder = painterResource(id = R.drawable.img_loading),
        model = model,
        contentScale = contentScale,
        modifier = modifier,
        contentDescription = contentDescription
    )
}

@Composable
fun ColorPellet(
    model: String?,
    action: (Color) -> Unit
) {
    var imagePainter: AsyncImagePainter? = null

    model?.let {
        imagePainter = rememberAsyncImagePainter(it)
    }

    LaunchedEffect(imagePainter) {
        imagePainter?.let {
            if (model?.isEmpty() == true) return@LaunchedEffect
            val bitmap =
                (it.imageLoader.execute(it.request).drawable as? BitmapDrawable)?.bitmap?.toNonHardwareBitmap()
                    ?: return@LaunchedEffect
            val palette = withContext(Dispatchers.Default) {
                bitmap.let { bitmap ->
                    Palette.from(bitmap).generate()
                }
            }
            palette.vibrantSwatch?.let { swatch ->
                action(Color(swatch.rgb).copy(alpha = 0.3f))
            }
        }
    }
}