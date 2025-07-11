package com.obidia.movieapp.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.util.Route
import com.obidia.movieapp.presentation.util.robotoFamily

@Composable
fun TopAppBar(
    isFirstItemVisible: Boolean,
    isTopBarVisible: Boolean,
    isTvShow: Boolean,
    isMovie: Boolean,
    category: String?,
    onClickClose: () -> Unit,
    onClickCategory: () -> Unit,
    onClickMovie: () -> Unit,
    onClickTvShow: () -> Unit,
    navigate: (Route) -> Unit,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isFirstItemVisible) Color.Transparent else MaterialTheme.colorScheme.background.copy(
            alpha = 0.8f
        ),
        label = "",
        animationSpec = tween(500, easing = FastOutLinearInEasing)
    )

    Column(
        modifier = Modifier.drawBehind {
            drawRect(animatedColor)
        }
    ) {
        Spacer(
            modifier = Modifier
                .height(
                    WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
        )
        TopBar()
        CategoryView(
            isTopBarVisible,
            isTvShow,
            isMovie,
            category,
            onClickClose,
            onClickCategory,
            onClickMovie,
            onClickTvShow
        )
    }
}

@Composable
fun CategoryView(
    isTopBarVisible: Boolean,
    isTvShow: Boolean,
    isMovie: Boolean,
    category: String?,
    onClickClose: () -> Unit,
    onClickCategory: () -> Unit,
    onClickMovie: () -> Unit,
    onClickTvShow: () -> Unit
) {
    AnimatedVisibility(
        visible = isTopBarVisible,
        enter = expandVertically(animationSpec = tween(300)),
        exit = shrinkVertically(animationSpec = tween(300))
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isMovie || !isTvShow) item {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .border(width = 1.dp, color = MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(200.dp))
                        .padding(4.dp)
                        .clickable {
                            onClickClose.invoke()
                        }
                        .animateItem(
                            fadeInSpec = tween(400),
                            fadeOutSpec = tween(400),
                            placementSpec = tween(400)
                        ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            if (isTvShow) item {
                CategoryItem(
                    "TV Shows", modifier = Modifier.animateItem(
                        fadeInSpec = tween(400),
                        fadeOutSpec = tween(400),
                        placementSpec = tween(400)
                    ),
                    oncClick = {
                        onClickTvShow.invoke()
                    }
                )
            }

            if (isMovie) item {
                CategoryItem(
                    "Movies", modifier = Modifier.animateItem(
                        fadeInSpec = tween(400),
                        fadeOutSpec = tween(400),
                        placementSpec = tween(400)
                    ),
                    oncClick = {
                        onClickMovie.invoke()
                    }
                )
            }

            if (!isMovie || !isTvShow) item {
                CategoryItem(
                    title = category ?: "Categories",
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(400),
                        fadeOutSpec = tween(400),
                        placementSpec = tween(400)
                    ),
                    oncClick = {
                        onClickCategory.invoke()
                    }
                )
            }
        }
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Text(
            text = "Jet Movie",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        )
    }
}