package com.obidia.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName
import com.obidia.movieapp.domain.model.CategoryModel

data class CategoryListResponse(
    @SerializedName("genres")
    val genres: List<Genre>?
)

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
) {
    companion object {
        fun transform(response: List<Genre>?): List<CategoryModel> {
            return response?.map {
                CategoryModel(
                    it.id ?: 0,
                    it.name ?: ""
                )
            } ?: listOf()
        }
    }
}