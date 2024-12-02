package com.obidia.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiStat())
    val uiState get() = _uiState

    @OptIn(FlowPreview::class)
    private fun getSearch(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                it.copy(listSearch = emptyFlow(), textSearch = query)
            }
            return
        }

        _uiState.update {
            it.copy(textSearch = query, listSearch = useCase.getSearch(query).debounce(2000L).map { pagingData ->
                pagingData.map { data -> data }
            }.cachedIn(viewModelScope))
        }
    }

    fun searchEvents(event: SearchEvents) {
        when (event) {
            is SearchEvents.OnAddTextSearch -> {
                getSearch(event.query)
            }

            is SearchEvents.OnClickClose -> {
                getSearch("")
            }
        }
    }
}

sealed class SearchEvents {
    data class OnAddTextSearch(val query: String) : SearchEvents()
    data object OnClickClose : SearchEvents()
}

data class SearchUiStat(
    val listSearch: Flow<PagingData<ItemModel>> = emptyFlow(),
    val textSearch: String = ""
)