package com.obidia.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName
import com.obidia.movieapp.domain.model.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponse(
    @SerializedName("genres")
    val genres: List<GenreMovieResponse>?
)

@Serializable
data class GenreMovieResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
) {
    companion object {
        fun transform(response: List<GenreMovieResponse>?): List<CategoryModel> {
            return response?.map {
                CategoryModel(
                    it.id ?: 0,
                    it.name ?: ""
                )
            } ?: listOf()
        }
    }
}