package com.obidia.movieapp.presentation.component

import kotlinx.serialization.Serializable

sealed interface Route

sealed interface MainScreen : Route

@Serializable
data object HomeScreenRoute : MainScreen

@Serializable
data object SearchScreenRoute : MainScreen

@Serializable
data object BookMarkScreenRoute : MainScreen