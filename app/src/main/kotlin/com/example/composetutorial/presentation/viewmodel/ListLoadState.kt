package com.example.composetutorial.presentation.viewmodel

import androidx.compose.runtime.Immutable

/**
 *
 * @author: est8
 * @date: 2024/6/17
 * 过程：RefreshIng->
 * 1. RefreshingSuccess
 * 2. RefreshingNoMoreData
 * 3. RefreshingLoadFailed
 *
 *  LoadingMoreIng->
 * 1. LoadingMoreSuccess
 * 2. LoadingMoreNoMoreData
 * 3. LoadingMoreLoadFailed
 *
 */
@Immutable
sealed class ListLoadState(
    val type: LoadType, val result: LoadResult = LoadResult.NONE, open val message: String? = null
) {

    data object GettingRefreshing : ListLoadState(LoadType.REFRESH)
    data object RefreshingSuccess : ListLoadState(LoadType.REFRESH, LoadResult.SUCCESS)
    data object RefreshingNoMoreData :
        ListLoadState(LoadType.REFRESH, LoadResult.NO_MORE_DATA, "Refreshing no more data...")

    data class RefreshingLoadFailed(override var message: String? = "Refreshing load failed...") :
        ListLoadState(LoadType.REFRESH, LoadResult.FAILED, message)

    data object GettingLoadingMore : ListLoadState(LoadType.LOAD_MORE)
    data object LoadingMoreSuccess : ListLoadState(LoadType.LOAD_MORE, LoadResult.SUCCESS)
    data object LoadingMoreNoMoreData :
        ListLoadState(LoadType.LOAD_MORE, LoadResult.NO_MORE_DATA, "Loading more no more data...")

    data class LoadingMoreLoadFailed(override var message: String? = "Loading more load failed...") :
        ListLoadState(LoadType.LOAD_MORE, LoadResult.FAILED, message)

    val isRefreshing
        get() = isRefreshing(this)
    val isRefreshSuccess
        get() = isRefreshSuccess(this)
    val isRefreshFailed
        get() = isRefreshFailed(this)
    val isRefreshNoMoreData
        get() = isRefreshNoMoreData(this)

    val isLoadingMore
        get() = isLoadingMore(this)
    val isLoadMoreSuccess
        get() = isLoadMoreSuccess(this)
    val isLoadMoreFailed
        get() = isLoadMoreFailed(this)
    val isLoadNoMoreData
        get() = isLoadMoreNoMoreData(this)

    val isNoMoreData
        get() = isLoadMoreNoMoreData(this) || isRefreshNoMoreData(this)


    companion object {
        fun isRefreshing(state: ListLoadState) =
            state.type == LoadType.REFRESH && state.result == LoadResult.NONE

        fun isRefreshSuccess(state: ListLoadState) =
            state.type == LoadType.REFRESH && state.result == LoadResult.SUCCESS

        fun isRefreshFailed(state: ListLoadState) =
            state.type == LoadType.REFRESH && state.result == LoadResult.FAILED

        fun isRefreshNoMoreData(state: ListLoadState) =
            state.type == LoadType.REFRESH && state.result == LoadResult.NO_MORE_DATA


        fun isLoadingMore(state: ListLoadState) =
            state.type == LoadType.LOAD_MORE && state.result == LoadResult.NONE

        fun isLoadMoreSuccess(state: ListLoadState) =
            state.type == LoadType.LOAD_MORE && state.result == LoadResult.SUCCESS

        fun isLoadMoreFailed(state: ListLoadState) =
            state.type == LoadType.LOAD_MORE && state.result == LoadResult.FAILED

        fun isLoadMoreNoMoreData(state: ListLoadState) =
            state.type == LoadType.LOAD_MORE && state.result == LoadResult.NO_MORE_DATA
    }


}

enum class LoadType {
    REFRESH, LOAD_MORE, NONE
}

enum class LoadResult {
    SUCCESS, NO_MORE_DATA, // 是 SUCCESS 的是一种
    FAILED, NONE
}
