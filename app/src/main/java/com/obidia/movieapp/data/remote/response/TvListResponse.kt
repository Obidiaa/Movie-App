package com.obidia.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName
import com.obidia.movieapp.domain.model.ItemModel

data class TvListResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<TvItemResponse>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

data class TvItemResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    companion object {
        fun transform(response: List<TvItemResponse>?): List<ItemModel> {
            return response?.map {
                ItemModel(
                    it.id ?: 0, it.posterPath ?: "", it.name ?: ""
                )
            } ?: listOf()
        }
    }
}