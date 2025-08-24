package com.obidia.movieapp.data.local.entiry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obidia.movieapp.domain.model.ItemModel

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("tmdb_id")
    val tmdbId: Int,
    @ColumnInfo("image")
    val image: String,
) {
    companion object {
        fun transform(item: ItemModel): MovieEntity {
            return MovieEntity(
                id = 0,
                tmdbId = item.id,
                image = item.image
            )
        }

        fun transform(item: List<MovieEntity>): ArrayList<ItemModel> {
            return ArrayList(
                item.map {
                    ItemModel(
                        id = it.tmdbId,
                        image = it.image,
                        title = ""
                    )
                }
            )
        }
    }
}