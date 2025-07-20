package com.obidia.movieapp.presentation.feature.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieBookmarkViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(MovieBookmarkUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMovieBookmarks()
    }

    private fun getMovieBookmarks() {
        viewModelScope.launch {
            useCase.getAllUser().collect { list ->
                Log.d("kesini list bookmark", list.toString())
                _uiState.update { it.copy(listMovie = list) }
            }
        }
    }
}

data class MovieBookmarkUiState(
    val listMovie: ArrayList<ItemModel> = arrayListOf()
)