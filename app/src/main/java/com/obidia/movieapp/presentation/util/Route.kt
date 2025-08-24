package com.obidia.movieapp.presentation.util

import kotlinx.serialization.Serializable

sealed interface Route

sealed interface MainScreenRoute : Route

@Serializable
data object HomeScreenRoute : MainScreenRoute

@Serializable
data object SearchScreenRoute : MainScreenRoute

@Serializable
data object BookMarkScreenRoute : MainScreenRoute

@Serializable
data class DetailScreenRoute(val movieId: Int) : Route