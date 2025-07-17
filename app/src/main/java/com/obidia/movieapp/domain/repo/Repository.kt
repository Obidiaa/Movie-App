package com.obidia.movieapp.domain.repo

import androidx.paging.PagingData
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.model.MovieDetailModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getMoviesNowPlaying(category: String?): Flow<PagingData<ItemModel>>

    fun getMoviesPopular(category: String?): Flow<PagingData<ItemModel>>

    fun getMovieTopRated(category: String?): Flow<PagingData<ItemModel>>

    fun getCategory(): Flow<Resource<List<CategoryModel>>>

    fun getCategoryTv(): Flow<Resource<List<CategoryModel>>>

    fun getTvAiringToday(category: String?): Flow<PagingData<ItemModel>>

    fun getTvPopular(category: String?): Flow<PagingData<ItemModel>>

    fun getTvTopRated(category: String?): Flow<PagingData<ItemModel>>

    fun getTvHeader(category: String?): Flow<Resource<ItemModel>>

    fun getMovieHeader(category: String?): Flow<Resource<ItemModel>>

    fun getTop10Movie(category: String?): Flow<Resource<List<ItemModel>>>

    fun getTop10Tv(): Flow<Resource<List<ItemModel>>>

    fun getSearch(query: String): Flow<PagingData<ItemModel>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailModel>>
}