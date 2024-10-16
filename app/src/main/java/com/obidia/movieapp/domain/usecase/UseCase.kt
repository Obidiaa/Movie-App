package com.obidia.movieapp.domain.usecase

import androidx.paging.PagingData
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.domain.model.ItemModel
import kotlinx.coroutines.flow.Flow

interface UseCase {

    fun getMoviesNowPlaying(category: String?): Flow<PagingData<ItemModel>>

    fun getMoviesNowPopular(category: String?): Flow<PagingData<ItemModel>>

    fun getMoviesTopRated(category: String?): Flow<PagingData<ItemModel>>

    fun getCategory(): Flow<Resource<List<CategoryModel>>>

    fun getCategoryTv(): Flow<Resource<List<CategoryModel>>>

    fun getTvAiringToday(category: String?): Flow<PagingData<ItemModel>>

    fun getTVPopular(category: String?): Flow<PagingData<ItemModel>>

    fun getTvTopRated(category: String?): Flow<PagingData<ItemModel>>

    fun getTvHeader(category: String?): Flow<Resource<ItemModel>>

    fun getMovieHeader(category: String?): Flow<Resource<ItemModel>>
}