package com.obidia.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.obidia.movieapp.data.remote.response.MovieItemResponse
import com.obidia.movieapp.data.repository.RemoteDataSource
import com.obidia.movieapp.domain.model.ItemModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviePopularPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val category: String?
) : PagingSource<Int, ItemModel>() {
    override fun getRefreshKey(state: PagingState<Int, ItemModel>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemModel> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = remoteDataSource.getMoviePopular(page = page, category = category)
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