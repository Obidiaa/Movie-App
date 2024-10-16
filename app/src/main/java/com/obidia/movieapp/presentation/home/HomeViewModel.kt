package com.obidia.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _listCategory =
        MutableStateFlow<Resource<List<CategoryModel>>?>(Resource.Success(listOf()))
    val listCategory get() = _listCategory

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState

    private val _filmState = MutableStateFlow(FilmUiState())
    val filmState get() = _filmState

    init {
        loadFilm()
        getMovieHeader(_uiState.value.categoryId)
    }

    private fun loadFilm(category: String? = null) {
        if (_uiState.value.isMovie) {
            getMoviesNowPlaying(category)
            getMoviesPopular(category)
            getMovieTopRated(category)
        }

        if (_uiState.value.isTvShow) {
            getTvPopular(category)
            getTvAiringToday(category)
            getTvTopRated(category)
        }

        if (_uiState.value.isTvShow && _uiState.value.isMovie) {
            getTop10Movie()
            getTop10Tv()
        }
    }

    private fun getMoviesNowPlaying(category: String?) {
        _filmState.update {
            it.copy(
                listMoviesNowPlaying = useCase.getMoviesNowPlaying(
                    category
                ).map { paging ->
                    paging.map { data -> data }
                }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getMoviesPopular(category: String?) {
        _filmState.update {
            it.copy(
                listMoviesPopular = useCase.getMoviesNowPopular(category)
                    .map { paging ->
                        paging.map { data -> data }
                    }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getMovieTopRated(category: String?) {
        _filmState.update {
            it.copy(
                listMovieTopRated = useCase.getMoviesTopRated(category)
                    .map { paging ->
                        paging.map { data -> data }
                    }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getTvAiringToday(category: String?) {
        _filmState.update {
            it.copy(
                listTvAiringToday = useCase.getTvAiringToday(category)
                    .map { paging ->
                        paging.map { data -> data }
                    }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getTvPopular(category: String?) {
        _filmState.update {
            it.copy(
                listTvPopular = useCase.getTVPopular(category)
                    .map { paging ->
                        paging.map { data -> data }
                    }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            useCase.getCategory()
                .stateIn(this, SharingStarted.WhileSubscribed(5000), null)
                .onEach {
                    _listCategory.value = it
                }.catch {
                    _listCategory.value = Resource.Error(it)
                }.collect()
        }
    }

    private fun getCategoryListTv() {
        viewModelScope.launch {
            useCase.getCategoryTv()
                .stateIn(this, SharingStarted.WhileSubscribed(5000), null)
                .onEach {
                    _listCategory.value = it
                }.catch {
                    _listCategory.value = Resource.Error(it)
                }.collect()
        }
    }

    private fun getTvTopRated(category: String?) {
        _filmState.update {
            it.copy(
                listTvTopRated = useCase.getTvTopRated(category)
                    .map { paging ->
                        paging.map { data -> data }
                    }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getTvHeader(category: String?) {
        viewModelScope.launch {
            useCase.getTvHeader(category).stateIn(this, SharingStarted.WhileSubscribed(5000), null)
                .onEach {
                    _filmState.value.filmHeader.value = it
                }.catch {
                    _filmState.value.filmHeader.value = Resource.Error(it)
                }.collect()
        }
    }

    private fun getMovieHeader(category: String?) {
        viewModelScope.launch {
            useCase.getMovieHeader(category)
                .stateIn(this, SharingStarted.WhileSubscribed(5000), null)
                .onEach {
                    _filmState.value.filmHeader.value = it
                }.catch {
                    _filmState.value.filmHeader.value = Resource.Error(it)
                }.collect()
        }
    }

    private fun getTop10Movie() {
        viewModelScope.launch {
            useCase.getTop10Movie()
                .stateIn(this, SharingStarted.WhileSubscribed(5000), null)
                .onEach {
                    _filmState.value.listTop10Movie.value = it
                }.catch {
                    _filmState.value.listTop10Movie.value = Resource.Error(it)
                }.collect()
        }
    }

    private fun getTop10Tv() {
        viewModelScope.launch {
            useCase.getTop10Tv()
                .stateIn(this, SharingStarted.WhileSubscribed(5000), null)
                .onEach {
                    _filmState.value.listTop10Tv.value = it
                }.catch {
                    _filmState.value.listTop10Tv.value = Resource.Error(it)
                }.collect()
        }
    }

    fun homeAction(action: HomeAction) {
        when (action) {
            is HomeAction.TopAppBarState -> _uiState.value = uiState.value.copy(
                isTopBarVisible = action.isTopBarVisible,
                isFirstItemVisible = action.isFirstItemVisible
            )

            is HomeAction.ContentVisible -> _uiState.value =
                uiState.value.copy(isVisibleContent = action.isContentVisible)

            is HomeAction.OnClickCategory -> {
                _uiState.value = uiState.value.copy(isShowCategoryDialog = true)
                if (_uiState.value.isMovie) {
                    getCategoryList()
                    return
                }

                if (_uiState.value.isTvShow) {
                    getCategoryListTv()
                    return
                }
            }

            is HomeAction.OnClickCategoryDialog -> {
                _uiState.value = uiState.value.copy(
                    isShowCategoryDialog = false,
                    categoryName = action.categoryName,
                    categoryId = action.categoryId
                )
            }

            is HomeAction.OnClickClose -> {
                if (_uiState.value.categoryId != null) loadFilm()

                if (_uiState.value.categoryId != null || _uiState.value.isTvShow)
                    getMovieHeader(null)

                _uiState.value = uiState.value.copy(
                    categoryName = "Categories",
                    isMovie = true,
                    isTvShow = true,
                    categoryId = null
                )
            }

            is HomeAction.OnClickTvShow -> {
                _uiState.value = uiState.value.copy(
                    isMovie = false
                )

                getTvHeader(null)
            }

            is HomeAction.OnDismissCategoryDialog -> {
                _uiState.value = uiState.value.copy(
                    isShowCategoryDialog = false
                )
            }

            is HomeAction.OnClickMovie -> {
                _uiState.value = uiState.value.copy(
                    isTvShow = false
                )
            }

            is HomeAction.OnLoadFilmByCategory -> {
                if (_uiState.value.categoryId != null) loadFilm(_uiState.value.categoryId)

                if (_uiState.value.isMovie && _uiState.value.categoryId != null) {
                    getMovieHeader(_uiState.value.categoryId)
                    return
                }

                if (_uiState.value.isTvShow) {
                    getTvHeader(_uiState.value.categoryId)
                    return
                }
            }
        }
    }
}

data class HomeUiState(
    val isFirstItemVisible: Boolean = true,
    val isTopBarVisible: Boolean = true,
    val categoryName: String? = null,
    val isTvShow: Boolean = true,
    val isMovie: Boolean = true,
    val isShowCategoryDialog: Boolean = false,
    val isVisibleContent: Boolean = true,
    val categoryId: String? = null
)

data class FilmUiState(
    val listMoviesNowPlaying: Flow<PagingData<ItemModel>> = emptyFlow(),
    val listMoviesPopular: Flow<PagingData<ItemModel>> = emptyFlow(),
    val listMovieTopRated: Flow<PagingData<ItemModel>> = emptyFlow(),
    val listTvAiringToday: Flow<PagingData<ItemModel>> = emptyFlow(),
    val listTvPopular: Flow<PagingData<ItemModel>> = emptyFlow(),
    val listTvTopRated: Flow<PagingData<ItemModel>> = emptyFlow(),
    var filmHeader: MutableStateFlow<Resource<ItemModel>?> = MutableStateFlow(null),
    var listTop10Movie: MutableStateFlow<Resource<List<ItemModel>>?> = MutableStateFlow(
        Resource.Success(
            listOf()
        )
    ),
    var listTop10Tv: MutableStateFlow<Resource<List<ItemModel>>?> = MutableStateFlow(
        Resource.Success(
            listOf()
        )
    )
)

sealed class HomeAction {
    data class TopAppBarState(
        val isTopBarVisible: Boolean,
        val isFirstItemVisible: Boolean
    ) : HomeAction()

    data class ContentVisible(val isContentVisible: Boolean) : HomeAction()

    data object OnClickClose : HomeAction()

    data object OnClickCategory : HomeAction()

    data object OnClickTvShow : HomeAction()

    data object OnClickMovie : HomeAction()

    data class OnClickCategoryDialog(val categoryName: String, val categoryId: String) :
        HomeAction()

    data object OnDismissCategoryDialog : HomeAction()

    data object OnLoadFilmByCategory : HomeAction()
}