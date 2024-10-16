package com.obidia.movieapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ItemModel(
    val id: Int,
    val image: String,
    val title: String,
)
