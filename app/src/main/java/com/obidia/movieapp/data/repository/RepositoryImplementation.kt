package com.obidia.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.obidia.movieapp.data.calendar
import com.obidia.movieapp.data.formatter
import com.obidia.movieapp.data.paging.GetMovieNowPlayingPagingSource
import com.obidia.movieapp.data.paging.GetMoviePopularPagingSource
import com.obidia.movieapp.data.paging.GetMovieTopRatedPagingSource
import com.obidia.movieapp.data.paging.GetSearchPagingSource
import com.obidia.movieapp.data.paging.GetTvAiringPagingSource
import com.obidia.movieapp.data.paging.GetTvPopularPagingSource
import com.obidia.movieapp.data.paging.GetTvTopRatedPagingSource
import com.obidia.movieapp.data.remote.response.Genre
import com.obidia.movieapp.data.remote.response.MovieItemResponse
import com.obidia.movieapp.data.remote.response.TvItemResponse
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.domain.repo.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImplementation @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override fun getMoviesNowPlaying(category: String?): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetMovieNowPlayingPagingSource(remoteDataSource, category = category)
            }
        ).flow
    }

    override fun getMoviesPopular(category: String?): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetMoviePopularPagingSource(remoteDataSource, category = category)
            }
        ).flow
    }

    override fun getMovieTopRated(category: String?): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetMovieTopRatedPagingSource(remoteDataSource, category = category)
            }
        ).flow
    }

    override fun getCategory(): Flow<Resource<List<CategoryModel>>> {
        return flow {
            emit(Resource.Loading)

            try {
                val response = remoteDataSource.getCategory()
                response.body()?.let {
                    val data = Genre.transform(it.genres)
                    emit(Resource.Success(data))
                } ?: kotlin.run {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }
    }

    override fun getCategoryTv(): Flow<Resource<List<CategoryModel>>> {
        return flow {
            emit(Resource.Loading)

            try {
                val response = remoteDataSource.getTvCategory()
                response.body()?.let {
                    val data = Genre.transform(it.genres)
                    emit(Resource.Success(data))
                } ?: kotlin.run {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }
    }

    override fun getTvAiringToday(category: String?): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetTvAiringPagingSource(remoteDataSource, category = category)
            }
        ).flow
    }

    override fun getTvPopular(category: String?): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetTvPopularPagingSource(remoteDataSource, category = category)
            }
        ).flow
    }

    override fun getTvTopRated(category: String?): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetTvTopRatedPagingSource(remoteDataSource, category = category)
            }
        ).flow
    }

    override fun getTvHeader(
        category: String?
    ): Flow<Resource<ItemModel>> {
        return flow {
            emit(Resource.Loading)

            try {
                val maxDate = formatter.format(Date())

                val minDate = formatter.format(
                    calendar.run {
                        add(Calendar.DAY_OF_YEAR, -7)
                        time
                    }
                )
                val response = remoteDataSource.getTvHeader(
                    category = category,
                    maxDate = maxDate,
                    minDate = minDate
                )

                response.body()?.let {
                    val data = TvItemResponse.transform(it.results)
                    if (data.isEmpty()) {
                        throw Throwable()
                    } else {
                        emit(Resource.Success(data[0]))
                    }
                } ?: kotlin.run {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }
    }

    override fun getMovieHeader(
        category: String?
    ): Flow<Resource<ItemModel>> {
        return flow {
            emit(Resource.Loading)

            try {
                val maxDate = formatter.format(Date())

                val minDate = formatter.format(
                    calendar.run {
                        add(Calendar.DAY_OF_YEAR, -7)
                        time
                    }
                )
                val response = remoteDataSource.getMovieHeader(
                    category = category,
                    maxDate = maxDate,
                    minDate = minDate
                )

                response.body()?.let {
                    val data = MovieItemResponse.transform(it.results)
                    if (data.isEmpty()) {
                        throw Throwable()
                    } else {
                        emit(Resource.Success(data[0]))
                    }
                } ?: kotlin.run {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }
    }

    override fun getTop10Movie(category: String?): Flow<Resource<List<ItemModel>>> {
        return flow {
            emit(Resource.Loading)

            try {
                val maxDate = formatter.format(Date())

                val minDate = formatter.format(
                    calendar.run {
                        add(Calendar.DAY_OF_YEAR, -7)
                        time
                    }
                )

                val response = remoteDataSource.getTop10Movie(category, maxDate, minDate)
                response.body()?.let {
                    val data = MovieItemResponse.transform(it.results)
                    if (data.isEmpty()) {
                        throw Throwable()
                    } else {
                        emit(Resource.Success(data.subList(0, 10)))
                    }
                } ?: kotlin.run {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }
    }

    override fun getTop10Tv(): Flow<Resource<List<ItemModel>>> {
        return flow {
            emit(Resource.Loading)

            try {
                val maxDate = formatter.format(Date())

                val minDate = formatter.format(
                    calendar.run {
                        add(Calendar.DAY_OF_YEAR, -7)
                        time
                    }
                )

                val response = remoteDataSource.getTop10Tv(maxDate, minDate)
                response.body()?.let {
                    val data = TvItemResponse.transform(it.results)
                    if (data.isEmpty()) {
                        throw Throwable()
                    } else {
                        emit(Resource.Success(data.subList(0, 10)))
                    }
                } ?: kotlin.run {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }
    }

    override fun getSearch(query: String): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetSearchPagingSource(remoteDataSource, query = query)
            }
        ).flow
    }
}