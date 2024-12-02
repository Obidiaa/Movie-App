package com.obidia.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName
import com.obidia.movieapp.domain.model.ItemModel

data class SearchListResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ItemSearch>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

data class ItemSearch(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("profile_path")
    val profilePath: Any?,
    @SerializedName("known_for")
    val knownFor: List<KnownFor?>?
) {
    companion object {
        fun transform(response: List<ItemSearch>?): List<ItemModel> {
            return response?.map {
                ItemModel(
                    it.id ?: 0,
                    it.posterPath ?: "",
                    it.title ?: ""
                )
            } ?: listOf()
        }
    }
}

data class KnownFor(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Int?
)