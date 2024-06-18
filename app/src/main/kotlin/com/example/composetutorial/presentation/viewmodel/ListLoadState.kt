package com.example.composetutorial.presentation.viewmodel

import androidx.compose.runtime.Immutable
import com.example.composetutorial.presentation.viewmodel.ListLoadState.Companion.LOADING_MORE_LOAD_FAILED
import com.example.composetutorial.presentation.viewmodel.ListLoadState.Companion.LOADING_MORE_NO_MORE_DATA
import com.example.composetutorial.presentation.viewmodel.ListLoadState.Companion.LOADING_MORE_SUCCESS
import com.example.composetutorial.presentation.viewmodel.ListLoadState.Companion.REFRESH_LOAD_FAILED
import com.example.composetutorial.presentation.viewmodel.ListLoadState.Companion.REFRESH_NO_MORE_DATA

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
sealed class ListLoadState(val type: Int, open val message: String? = null) {

    data object GettingRefreshing : ListLoadState(STATE_GETTING_REFRESHING)
    data object RefreshingSuccess : ListLoadState(REFRESH_SUCCESS)
    data object RefreshingNoMoreData : ListLoadState(REFRESH_NO_MORE_DATA, "Refreshing no more data...")
    data class RefreshingLoadFailed(override var message: String? = "refreshing load failed...") :
        ListLoadState(REFRESH_LOAD_FAILED, message)

    data object GettingLoadingMore : ListLoadState(STATE_GETTING_LOADING_MORE)
    data object LoadingMoreSuccess : ListLoadState(LOADING_MORE_SUCCESS)
    data object LoadingMoreNoMoreData : ListLoadState(LOADING_MORE_NO_MORE_DATA, "Loading more no more data...")
    data class LoadingMoreLoadFailed(override var message: String? = "loading more load failed...") :
        ListLoadState(LOADING_MORE_LOAD_FAILED, message)


    val isGettingRefreshing = this is GettingRefreshing
    val isGettingLoadingMore = this is GettingLoadingMore

    val isRefreshingSuccess = this is RefreshingSuccess
    val isLoadingMoreSuccess = this is LoadingMoreSuccess

    val isNoMoreData = this is RefreshingNoMoreData || this is LoadingMoreNoMoreData
    val isLoadFailed = this is RefreshingLoadFailed || this is LoadingMoreLoadFailed

    val isRefreshingNoMoreData = this is RefreshingNoMoreData
    val isRefreshingLoadFailed = this is RefreshingLoadFailed

    val isLoadingMoreNoMoreData = this is LoadingMoreNoMoreData
    val isLoadingMoreLoadFailed = this is LoadingMoreLoadFailed

    companion object {

        // 大状态
        private val STATE_NONE = 0b000_00_00 // 0 无状态
        private val STATE_REFRESHING = 0b000_00_01 // 1 刷新
        private val STATE_LOADING_MORE = 0b000_00_10 // 2 加载更多

        // 中间过程状态
        private val STATE_GETTING_REFRESHING = 0b000_01_00 // 4 获取刷新中
        private val STATE_GETTING_LOADING_MORE = 0b000_10_00 // 8 获取加载更多中

        // 结果状态
        private val RESULT_NONE = 0b0000000 // 0 无结果状态
        private val RESULT_NO_MORE_DATA = 0b001_00_00 // 4 无更多数据
        private val RESULT_LOAD_FAILED = 0b010_00_00 // 8 加载失败
        private val RESULT_SUCCESS = 0b100_00_00 // 16 加载成功

        // 定义状态和结果状态的掩码
        private val MASK_STATE = 0b000_00_11 // 掩码用于提取大状态
        private val MASK_MIDDLE_STATE = 0b000_00_00 // 掩码用于提取结果状态
        private val MASK_RESULT = 0b111_00_00 // 掩码用于提取结果状态

        private val REFRESH_SUCCESS = STATE_REFRESHING or RESULT_SUCCESS
        private val REFRESH_NO_MORE_DATA = STATE_REFRESHING or RESULT_NO_MORE_DATA
        private val REFRESH_LOAD_FAILED = STATE_REFRESHING or RESULT_LOAD_FAILED

        private val LOADING_MORE_SUCCESS = STATE_LOADING_MORE or RESULT_SUCCESS
        private val LOADING_MORE_NO_MORE_DATA = STATE_LOADING_MORE or RESULT_NO_MORE_DATA
        private val LOADING_MORE_LOAD_FAILED = STATE_LOADING_MORE or RESULT_LOAD_FAILED

        fun checkState(state: Int, flag: Int): Boolean {
            return (state and flag) == flag
        }

        // 获取currentState 是刷新还是加载更多
        fun getCurrentState(state: Int): Int {
            // 提取大状态：使用掩码 MASK_STATE 来提取大状态的位段，并与想要检查的状态进行比较。
            return state and MASK_STATE
        }

        // 获取resultState 是无更多数据还是加载失败
        fun getResultState(state: Int): Int {
            // 提取结果状态：使用掩码 MASK_RESULT 来提取结果状态的位段，并与想要检查的状态进行比较。
            return state and MASK_RESULT
        }


        fun isRefreshing(state: Int): Boolean {
            return checkState(state, STATE_REFRESHING)
        }

        fun isLoadingMore(state: Int): Boolean {
            return checkState(state, STATE_LOADING_MORE)
        }

        fun isNoMoreData(state: Int): Boolean {
            return checkState(state, RESULT_NO_MORE_DATA)
        }

        fun isLoadFailed(state: Int): Boolean {
            return checkState(state, RESULT_LOAD_FAILED)
        }

        fun isLoadSuccess(state: Int): Boolean {
            return checkState(state, RESULT_SUCCESS)
        }

        fun isRefreshingNoMoreData(state: Int): Boolean {
            return checkState(state, REFRESH_NO_MORE_DATA)
        }

        fun isRefreshingLoadFailed(state: Int): Boolean {
            return checkState(state, REFRESH_LOAD_FAILED)
        }

        fun isLoadingMoreSuccess(state: Int): Boolean {
            return checkState(state, LOADING_MORE_SUCCESS)
        }

        fun isLoadingMoreNoMoreData(state: Int): Boolean {
            return checkState(state, LOADING_MORE_NO_MORE_DATA)
        }

        fun isLoadingMoreLoadFailed(state: Int): Boolean {
            return checkState(state, LOADING_MORE_LOAD_FAILED)
        }
    }
}


