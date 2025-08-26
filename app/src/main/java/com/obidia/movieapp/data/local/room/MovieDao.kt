package com.obidia.movieapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obidia.movieapp.data.local.entiry.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllUser(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: MovieEntity)

    @Query("DELETE FROM movie WHERE tmdb_id = :tmdbId")
    fun deleteUser(tmdbId: Int)

    @Query("SELECT * FROM movie WHERE tmdb_id = :tmdbId")
    fun checkSameDataUser(tmdbId: Int): Flow<List<MovieEntity>>
}