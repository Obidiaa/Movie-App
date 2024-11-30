package com.obidia.movieapp.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obidia.movieapp.R

@Composable
fun CategoryItem(title: String?, oncClick: () -> Unit, modifier: Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.Transparent)
            .border(
                border = BorderStroke(1.dp, color = Color.Red),
                shape = RoundedCornerShape(80.dp)
            )
            .clickable {
                oncClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title ?: "",
            color = Color.Red,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(
                    start = 12.dp,
                    end = if (title != "Movies" && title != "TV Shows") 6.dp else 12.dp
                ),
            fontSize = 14.sp
        )

        if (title != "Movies" && title != "TV Shows") {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 12.dp)
                    .size(14.dp),
                tint = Color.Red
            )
        }
    }
}

@Preview
@Composable
fun PreviewCategoryItem() {
    CategoryItem("Movie", oncClick = {}, modifier = Modifier)
}