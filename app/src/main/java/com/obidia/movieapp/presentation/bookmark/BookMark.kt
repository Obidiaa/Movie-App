package com.obidia.movieapp.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.obidia.movieapp.presentation.component.BookMarkScreenRoute
import com.obidia.movieapp.presentation.component.Route

fun NavGraphBuilder.bookMarkScreenRoute(navigate: (Route) -> Unit) {
    composable<BookMarkScreenRoute> {
        BookMarkScreen()
    }
}

@Composable
fun BookMarkScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Book Mark")
    }
}