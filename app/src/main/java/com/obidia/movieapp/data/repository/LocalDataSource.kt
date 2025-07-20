package com.obidia.movieapp.data.repository

import com.obidia.movieapp.data.local.entiry.MovieEntity
import com.obidia.movieapp.data.local.room.MovieDao
import com.obidia.movieapp.domain.model.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val dao: MovieDao
) {
    fun cekSameDataUser(id: Int): Flow<Boolean> {
        return dao.checkSameDataUser(id).map {
            it.isNotEmpty()
        }.flowOn(Dispatchers.IO)
    }

    fun deleteUser(id: Int) {
        dao.deleteUser(id)
    }

    fun addUser(movie: ItemModel) {
        dao.addUser(MovieEntity.transform(movie))
    }

    fun getAllUser(): Flow<ArrayList<ItemModel>> {
        return dao.getAllUser().map {
            MovieEntity.transform(it)
        }.flowOn(Dispatchers.IO)
    }
}