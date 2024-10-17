package com.obidia.movieapp.presentation.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.obidia.movieapp.R
import com.obidia.movieapp.presentation.component.Route
import com.obidia.movieapp.presentation.component.SearchScreenRoute
import com.obidia.movieapp.presentation.component.robotoFamily
import com.obidia.movieapp.ui.theme.neutral2
import com.obidia.movieapp.ui.theme.neutral5

fun NavGraphBuilder.searchScreenRout(navigate: (Route) -> Unit) {
    composable<SearchScreenRoute> {
        SearchScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()

        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = neutral2,
                        unfocusedBorderColor = neutral2,
                        unfocusedContainerColor = neutral2,
                        focusedContainerColor = neutral2,
                    ),
                    value = "",
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Search Movie",
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                            contentDescription = ""
                        )
                    },
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            shape = RectangleShape,
                            enabled = true,
                            isError = false,
                            interactionSource = remember { MutableInteractionSource() },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = neutral2,
                                unfocusedBorderColor = neutral2,
                                unfocusedContainerColor = neutral2,
                                focusedContainerColor = neutral2,
                            )
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun TopBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = neutral5,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                text = "For Obid",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier.padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    tint = neutral5,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark),
                    contentDescription = "bookmark"
                )

                Icon(
                    tint = neutral5,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = "search"
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}