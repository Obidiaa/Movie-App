package com.obidia.movieapp.presentation.component

import kotlinx.serialization.Serializable

sealed interface Route

@Serializable
data object HomeScreenRoute : Route

@Serializable
data object SearchScreenRoute : Route