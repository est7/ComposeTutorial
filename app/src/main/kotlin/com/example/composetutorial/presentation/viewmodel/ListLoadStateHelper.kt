package com.example.composetutorial.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

/**
 *
 * @author: est8
 * @date: 2024/6/25
 */

class ListLoadStateHelper(
    val isRefreshing: State<Boolean>,
    val isGettingLoadingMore: State<Boolean>,
    val noMoreData: State<Boolean>,
    val loadFailed: State<Boolean>,
    val errorMessage: State<String?>
)
@Composable
fun rememberListLoadStateHelper(state: ListLoadState): ListLoadStateHelper {
    val isRefreshing by remember { derivedStateOf { state.isRefreshing } }
    val isGettingLoadingMore by remember { derivedStateOf { state.isLoadingMore } }
    val noMoreData by remember { derivedStateOf { state.isNoMoreData } }
    val loadFailed by remember { derivedStateOf { state.isLoadMoreFailed } }
    val errorMessage by remember { derivedStateOf { (state as? ListLoadState.LoadingMoreLoadFailed)?.message } }

    return ListLoadStateHelper(
        isRefreshing = rememberUpdatedState(isRefreshing),
        isGettingLoadingMore = rememberUpdatedState(isGettingLoadingMore),
        noMoreData = rememberUpdatedState(noMoreData),
        loadFailed = rememberUpdatedState(loadFailed),
        errorMessage = rememberUpdatedState(errorMessage)
    )
}