package com.obidia.movieapp.presentation.util

import kotlinx.serialization.Serializable

sealed interface Route

sealed interface MainScreen : Route

@Serializable
data object HomeScreenRoute : MainScreen

@Serializable
data object SearchScreenRoute : MainScreen

@Serializable
data object BookMarkScreenRoute : MainScreen

@Serializable
data class DetailScreenRoute(val movieId: Int) : Route