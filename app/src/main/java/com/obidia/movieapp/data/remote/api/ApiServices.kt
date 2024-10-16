package com.obidia.movieapp.data.remote.api

import com.obidia.movieapp.data.remote.response.CategoryListResponse
import com.obidia.movieapp.data.remote.response.MovieListResponse
import com.obidia.movieapp.data.remote.response.TvListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("discover/movie")
    suspend fun getMovieNowPlaying(
        @Query("page") page: Int,
        @Query("with_release_type") releaseType: String = "2|3",
        @Query("release_date.gte") minDate: String,
        @Query("release_date.lte") maxDate: String,
        @Query("with_genres") genres: String? = null,
        @Query("sort_by") sortBy: String = "primary_release_date.desc",
    ): MovieListResponse

    @GET("discover/movie")
    suspend fun getTop10Movie(
        @Query("page") page: Int = 1,
        @Query("release_date.gte") minDate: String,
        @Query("release_date.lte") maxDate: String,
        @Query("with_genres") genres: String? = null,
        @Query("sort_by") sortBy: String = "vote_average.desc",
    ): Response<MovieListResponse>

    @GET("discover/movie")
    suspend fun getMoviePopular(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genres: String? = null
    ): MovieListResponse

    @GET("discover/movie")
    suspend fun getMovieTopRated(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "vote_average.desc",
        @Query("with_genres") genres: String? = null,
        @Query("vote_count.gte") voteCount: String = "200"
    ): MovieListResponse

    @GET("discover/tv")
    suspend fun getTvAiringToday(
        @Query("page") page: Int,
        @Query("air_date.gte") minDate: String,
        @Query("air_date.lte") maxDate: String,
        @Query("with_genres") genres: String? = null,
        @Query("sort_by") sortBy: String = "first_air_date.desc",
    ): TvListResponse

    @GET("discover/tv")
    suspend fun getTvPopular(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genres: String? = null
    ): TvListResponse

    @GET("discover/tv")
    suspend fun getTvTopRated(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "vote_average.desc",
        @Query("with_genres") genres: String? = null,
        @Query("vote_count.gte") voteCount: String = "200"
    ): TvListResponse

    @GET("genre/movie/list")
    suspend fun getCategoryMovie(
        @Query("language") language: String = "en"
    ): Response<CategoryListResponse>

    @GET("genre/tv/list")
    suspend fun getCategoryTv(
        @Query("language") language: String = "en"
    ): Response<CategoryListResponse>

    @GET("discover/movie")
    suspend fun getMovieHeader(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genres: String? = null,
        @Query("release_date.gte") minDate: String,
        @Query("release_date.lte") maxDate: String,
    ): Response<MovieListResponse>

    @GET("discover/tv")
    suspend fun getTvHeader(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genres: String? = null,
        @Query("air_date.gte") minDate: String,
        @Query("air_date.lte") maxDate: String,
    ): Response<TvListResponse>
}