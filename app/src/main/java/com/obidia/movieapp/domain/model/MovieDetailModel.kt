package com.obidia.movieapp.domain.model

data class MovieDetailModel(
    val image: String,
    val title: String,
    val date: String,
    val isBookmark: Boolean,
    val listGenre: List<String>,
    val tagline: String,
    val description: String,
    val originalTitle: String,
    val runTime: String,
    val voteCount: Int,
    val voteAverage: Double,
    val listRecommendation: List<ItemModel>
)
