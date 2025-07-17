package com.obidia.movieapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.obidia.movieapp.domain.model.MovieDetailModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: MovieDetailBelongsToCollection? = null,
    @SerializedName("budget")
    val budget: Int? = null,
    @SerializedName("genres")
    val genres: List<MovieDetailGenreResponse?>? = null,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String?>? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("revenue")
    val revenue: Int? = null,
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) {
    companion object {
        fun transform(response: MovieDetailResponse) = MovieDetailModel(
            backdropPath = "https://image.tmdb.org/t/p/w500/${response.backdropPath ?: ""}",
            posterPath = "https://image.tmdb.org/t/p/w500/${response.backdropPath ?: ""}",
            title = response.title ?: "",
            releaseDate = response.releaseDate ?: "",
            isBookmark = false,
            genres = response.genres?.map { it?.name ?: "" } ?: listOf(),
            overview = response.overview ?: "",
            originalTitle = response.originalTitle ?: "",
            runtime = response.runtime.toString(),
            voteAverage = response.voteAverage ?: 0.0,
            listRecommendation = listOf(),
            budget = response.budget ?: 0,
            revenue = response.revenue ?: 0
        )
    }
}

@Serializable
data class MovieDetailBelongsToCollection(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null
)

@Serializable
data class MovieDetailGenreResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)

@Serializable
data class ProductionCompany(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("logo_path")
    val logoPath: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("origin_country")
    val originCountry: String? = null
)

@Serializable
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String? = null,
    @SerializedName("name")
    val name: String? = null
)

@Serializable
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String? = null,
    @SerializedName("iso_639_1")
    val iso6391: String? = null,
    @SerializedName("name")
    val name: String? = null
)