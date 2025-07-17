package com.obidia.movieapp.domain.model

data class MovieDetailModel(
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val runtime: String,
    val voteAverage: Double,
    val genres: List<String>,
    val budget: Int,
    val revenue: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val isBookmark: Boolean?,
    val listRecommendation: List<ItemModel>,
    val voteCount: String,
    val originalLanguage: String,
    val originalCountry: String,
    val imdbId: String
)
