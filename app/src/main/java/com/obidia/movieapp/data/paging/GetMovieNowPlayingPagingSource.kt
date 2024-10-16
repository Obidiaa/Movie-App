package com.obidia.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.obidia.movieapp.data.calendar
import com.obidia.movieapp.data.formatter
import com.obidia.movieapp.data.remote.response.MovieItemResponse
import com.obidia.movieapp.data.repository.RemoteDataSource
import com.obidia.movieapp.domain.model.ItemModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovieNowPlayingPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val category: String?
) : PagingSource<Int, ItemModel>() {

    private val maxDate = formatter.format(Date())

    private val minDate = formatter.format(
        calendar.run {
            add(Calendar.DAY_OF_YEAR, -14)
            time
        }
    )

    override fun getRefreshKey(state: PagingState<Int, ItemModel>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemModel> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                remoteDataSource.getMovieNowPlaying(
                    page = page,
                    minDate = minDate,
                    maxDate = maxDate,
                    category = category
                )
            val list = MovieItemResponse.transform(response.results)
            LoadResult.Page(
                data = list,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1

    }

}