package com.obidia.movieapp.presentation.feature.detail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.model.MovieDetailModel
import com.obidia.movieapp.domain.usecase.UseCase
import com.obidia.movieapp.presentation.util.DetailScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCase: UseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMovieDetail(movieId = savedStateHandle.toRoute<DetailScreenRoute>().movieId)
    }

    private fun checkMovieIsFavorite() = viewModelScope.launch {
        _uiState.value.movieDetail?.id?.let { idMovie ->
            useCase.cekSameDataUser(idMovie).collect { isFav ->
                _uiState.update {
                    it.copy(isFavorite = isFav)
                }
            }
        }
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            useCase.getMovieDetail(movieId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {}

                    is Resource.Loading -> {}

                    is Resource.Success<MovieDetailModel> -> {
                        _uiState.update { it.copy(movieDetail = resource.data) }
                        checkMovieIsFavorite()
                    }
                }
            }
        }
    }

    fun action(action: MovieDetailAction) {
        when (action) {
            is MovieDetailAction.OnChangeTopBarColor -> {
                _uiState.update {
                    it.copy(topBarColor = action.color)
                }
            }

            is MovieDetailAction.OnClickBookmark -> {
                val data = _uiState.value.movieDetail
                if (!_uiState.value.isFavorite) {
                    viewModelScope.launch(Dispatchers.IO) {
                        data?.let { movie ->
                            useCase.addUser(
                                ItemModel(
                                    id = movie.id,
                                    title = movie.title,
                                    image = movie.posterPath,
                                )
                            )
                        }
                    }
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        data?.id?.let {
                            useCase.deleteUser(it)
                        }
                    }
                }

                checkMovieIsFavorite()
            }
        }
    }
}

data class MovieDetailUiState(
    val movieDetail: MovieDetailModel? = null,
    val topBarColor: Color? = null,
    val isFavorite: Boolean = false
) {
    fun mock() = MovieDetailUiState(
        MovieDetailModel(
            title = "The Endless Summer",
            originalTitle = "The Endless Summer",
            overview = "Bruce Brown's The Endless Summer is one of the first and most influential surf movies of all time. The film documents American surfers Mike Hynson and Robert August as they travel the world during California’s winter (which, back in 1965 was off-season for surfing) in search of the perfect wave and ultimately, an endless summer.",
            releaseDate = "1966-06-15",
            runtime = 91.toString(),
            voteAverage = 7.238,
            genres = listOf("Documentary"),
            budget = 50000,
            revenue = 0,
            posterPath = "/hinHiXl8sOGIz2TLIZNpO5rhjND.jpg", // This would be a full URL in a real app
            backdropPath = "/uNRfK14Ga8Hwfqt07vo8nvWQN1i.jpg", // This would be a full URL in a real app
            isBookmark = false,
            listRecommendation = listOf(),
            "", "", "", "", 0
        )
    )
}

sealed class MovieDetailAction {
    data class OnChangeTopBarColor(val color: Color) : MovieDetailAction()
    data object OnClickBookmark : MovieDetailAction()
}