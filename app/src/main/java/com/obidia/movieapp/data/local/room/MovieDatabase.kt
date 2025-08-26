package com.obidia.movieapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obidia.movieapp.data.local.entiry.MovieEntity

@Database(entities = [MovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}