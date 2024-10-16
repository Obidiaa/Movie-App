package com.obidia.movieapp.domain.usecase

import androidx.paging.PagingData
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.repo.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCaseImplementation @Inject constructor(
    private val repository: Repository
) : UseCase {
    override fun getMoviesNowPlaying(category: String?): Flow<PagingData<ItemModel>> {
        return repository.getMoviesNowPlaying(category = category)
    }

    override fun getMoviesNowPopular(category: String?): Flow<PagingData<ItemModel>> {
        return repository.getMoviesPopular(category = category)
    }

    override fun getMoviesTopRated(category: String?): Flow<PagingData<ItemModel>> {
        return repository.getMovieTopRated(category = category)
    }

    override fun getCategory(): Flow<Resource<List<CategoryModel>>> {
        return repository.getCategory()
    }

    override fun getCategoryTv(): Flow<Resource<List<CategoryModel>>> {
        return repository.getCategoryTv()
    }

    override fun getTvAiringToday(category: String?): Flow<PagingData<ItemModel>> {
        return repository.getTvAiringToday(category = category)
    }

    override fun getTVPopular(category: String?): Flow<PagingData<ItemModel>> {
        return repository.getTvPopular(category)
    }

    override fun getTvTopRated(category: String?): Flow<PagingData<ItemModel>> {
        return repository.getTvTopRated(category)
    }

    override fun getTvHeader(category: String?): Flow<Resource<ItemModel>> {
        return repository.getTvHeader(category)
    }

    override fun getMovieHeader(category: String?): Flow<Resource<ItemModel>> {
        return repository.getMovieHeader(category)
    }

    override fun getTop10Movie(): Flow<Resource<List<ItemModel>>> {
        return repository.getTop10Movie(null)
    }

    override fun getTop10Tv(): Flow<Resource<List<ItemModel>>> {
        return repository.getTop10Tv()
    }
}