package com.capstone.kuma.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.kuma.LoginSession
import com.capstone.kuma.api.ApiService
import com.capstone.kuma.api.moodResult

class MoodPagingSource(private val apiService: ApiService, private val loginSession: LoginSession): PagingSource<Int, moodResult>() {
    override fun getRefreshKey(state: PagingState<Int, moodResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, moodResult> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMoodByID("Bearer ${loginSession.token}", position, params.loadSize)

            LoadResult.Page(
                data = responseData.moodResult,
                prevKey = if (position == 1) null else position - 1,
                nextKey = null
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}