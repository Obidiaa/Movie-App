package com.obidia.movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import com.obidia.movieapp.domain.model.MovieDetailModel
import com.obidia.movieapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()
}

data class MovieDetailUiState(
    val movieDetail: MovieDetailModel? = null,
) {
    fun mock() = MovieDetailUiState(
        MovieDetailModel(
            image = "",
            title = "Blade Runner 2049",
            date = "12-12-2017",
            isBookmark = true,
            listGenre = listOf("Action", "Sci-Fi", "Sport", "Shounen", "Thriller", "Mystery"),
            tagline = "“The Search for the Perfect Wave!”",
            description = "Bruce Brown's The Endless Summer is one of the first and most influential surf movies of all time. The film documents American surfers Mike Hynson and Robert August as they travel the world during California’s winter (which, back in 1965 was off-season for surfing) in search of the perfect wave and ultimately, an endless summer.",
            originalTitle = "Blade Runner 2049",
            runTime = "91 minutes",
            voteCount = 1200,
            voteAverage = 8.0,
            listRecommendation = listOf()
        )
    )
}