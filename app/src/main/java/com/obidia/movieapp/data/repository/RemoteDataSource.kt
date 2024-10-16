package com.obidia.movieapp.data.repository

import com.obidia.movieapp.data.remote.api.ApiServices
import com.obidia.movieapp.data.remote.response.CategoryListResponse
import com.obidia.movieapp.data.remote.response.MovieListResponse
import com.obidia.movieapp.data.remote.response.TvListResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) {

    suspend fun getMovieNowPlaying(
        page: Int,
        minDate: String,
        maxDate: String,
        category: String?
    ): MovieListResponse {
        return apiServices.getMovieNowPlaying(
            page,
            minDate = minDate,
            maxDate = maxDate,
            genres = category
        )
    }

    suspend fun getMoviePopular(
        page: Int,
        category: String?
    ): MovieListResponse {
        return apiServices.getMoviePopular(page, genres = category)
    }

    suspend fun getMovieTopRated(
        page: Int,
        category: String?
    ): MovieListResponse {
        return apiServices.getMovieTopRated(page, genres = category)
    }

    suspend fun getTvAiringToday(
        page: Int,
        category: String?,
        minDate: String,
        maxDate: String,
    ): TvListResponse {
        return apiServices.getTvAiringToday(
            page = page,
            genres = category,
            minDate = minDate,
            maxDate = maxDate
        )
    }

    suspend fun getTvPopular(
        page: Int,
        category: String?
    ): TvListResponse {
        return apiServices.getTvPopular(page, genres = category)
    }

    suspend fun getTvTopRated(
        page: Int,
        category: String?
    ): TvListResponse {
        return apiServices.getTvTopRated(page, genres = category)
    }

    suspend fun getCategory(): Response<CategoryListResponse> {
        return apiServices.getCategoryMovie()
    }

    suspend fun getTvCategory(): Response<CategoryListResponse> {
        return apiServices.getCategoryTv()
    }

    suspend fun getMovieHeader(
        category: String?,
        maxDate: String,
        minDate: String,
    ): Response<MovieListResponse> {
        return apiServices.getMovieHeader(
            genres = category,
            minDate = minDate,
            maxDate = maxDate
        )
    }

    suspend fun getTvHeader(
        category: String?,
        maxDate: String,
        minDate: String,
    ): Response<TvListResponse> {
        return apiServices.getTvHeader(
            genres = category,
            minDate = minDate,
            maxDate = maxDate
        )
    }

    suspend fun getTop10Movie(
        category: String?,
        maxDate: String,
        minDate: String,
    ): Response<MovieListResponse> {
        return apiServices.getTop10Movie(
            genres = category,
            maxDate = maxDate,
            minDate = minDate
        )
    }

    suspend fun getTop10Tv(
        maxDate: String,
        minDate: String,
    ): Response<TvListResponse> {
        return apiServices.getTop10Tv(
            maxDate = maxDate,
            minDate = minDate
        )
    }
}