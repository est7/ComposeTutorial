package com.example.composetutorial.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.data.dto.PagedData
import com.example.composetutorial.presentation.page.SubFollowPageAction
import com.example.composetutorial.presentation.usecase.GetFollowSubListUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
class SubFollowViewModel(
    val handle: SavedStateHandle,
    private val getFollowSubListUseCase: GetFollowSubListUseCase,
) : ViewModel() {

    private var pagedData = PagedData<ComposeTipsItemDTO>(data = persistentListOf(), currentPage = 1, hasMore = true)

    private val _subFollowScreenUiState = MutableSharedFlow<FollowSubPageScreenUiState>()
    val subFollowScreenUiState: StateFlow<FollowSubPageScreenUiState> =
        _subFollowScreenUiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FollowSubPageScreenUiState.Initial)

    private val _sideEffect: Channel<FollowSubPageScreenSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()


    fun onSubFollowAction(action: SubFollowPageAction) {
        when (action) {
            is SubFollowPageAction.LoadMore -> {
                loadMoreFollowSubPageList(action.type)
            }
            is SubFollowPageAction.Refresh -> {
                refreshSubFollowPageList(action.type)
            }
            is SubFollowPageAction.onClickFollow -> {

            }

            is SubFollowPageAction.onClickRemove -> {

            }

            else -> return
        }
    }

    private fun refreshSubFollowPageList(type: String) {
        fetchData(type, page = 1, isRefresh = true)
    }

    private fun loadMoreFollowSubPageList(type: String) {
        if (pagedData.hasMore) {
            fetchData(type, pagedData.currentPage + 1, isRefresh = false)
        }
    }

    private fun fetchData(type: String, page: Int, isRefresh: Boolean) {
        viewModelScope.launch {
            updateUiState(isRefresh)

            getFollowSubListUseCase(type, page).collect { result ->
                result.fold(onSuccess = { data ->
                    handleSuccess(data, page, isRefresh)
                }, onFailure = { exception ->
                    handleFailure(exception, isRefresh)
                })
            }
        }
    }

    private fun updateUiState(isRefresh: Boolean) {
        viewModelScope.launch {
            if (isRefresh) {
                if (pagedData.data.isEmpty()) {
                    _subFollowScreenUiState.emit(FollowSubPageScreenUiState.EmptyLoading)
                } else {
                    _subFollowScreenUiState.emit(
                        FollowSubPageScreenUiState.Loaded(
                            data = pagedData.data,
                            listState = ListLoadState.GettingRefreshing,
                        )
                    )
                }
            } else {
                _subFollowScreenUiState.emit(
                    FollowSubPageScreenUiState.Loaded(
                        data = pagedData.data,
                        listState = ListLoadState.GettingLoadingMore,
                    )
                )
            }
        }
    }

    private suspend fun handleSuccess(data: ImmutableList<ComposeTipsItemDTO>, page: Int, isRefresh: Boolean) {
        val noMoreData = data.size < PAGE_SIZE
        pagedData = if (isRefresh) {
            PagedData(data = data, currentPage = 1, hasMore = !noMoreData)
        } else {
            PagedData(
                data = if (pagedData.data.isEmpty()) data else (pagedData.data + data).toImmutableList(),
                currentPage = page,
                hasMore = !noMoreData
            )
        }

        emitLoadedState(
            if (isRefresh) {
                if (noMoreData) ListLoadState.RefreshingNoMoreData else ListLoadState.RefreshingSuccess
            } else {
                if (noMoreData) ListLoadState.LoadingMoreNoMoreData else ListLoadState.LoadingMoreSuccess
            }
        )
    }

    private suspend fun handleFailure(exception: Throwable, isRefresh: Boolean) {
        if (pagedData.data.isEmpty()) {
            _subFollowScreenUiState.emit(FollowSubPageScreenUiState.LoadFailed(exception.message ?: "Unknown error"))
        } else {
            emitLoadedState(
                if (isRefresh) ListLoadState.RefreshingLoadFailed(exception.message ?: "Unknown error")
                else ListLoadState.LoadingMoreLoadFailed(exception.message ?: "Unknown error")
            )
        }

        postSideEffect(FollowSubPageScreenSideEffect.ShowToast(exception.message ?: "Unknown error"))
    }

    private suspend fun emitLoadedState(state: ListLoadState) {
        _subFollowScreenUiState.emit(
            FollowSubPageScreenUiState.Loaded(data = pagedData.data, listState = state)
        )
    }

    private fun postSideEffect(followScreenUiState: FollowSubPageScreenSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(followScreenUiState)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

sealed class FollowSubPageScreenUiState {
    data object Initial : FollowSubPageScreenUiState()
    data object EmptyLoading : FollowSubPageScreenUiState()
    data class Loaded(val listState: ListLoadState, val data: ImmutableList<ComposeTipsItemDTO>) :
        FollowSubPageScreenUiState()

    data object Empty : FollowSubPageScreenUiState()
    data class LoadFailed(val message: String) : FollowSubPageScreenUiState()
}


sealed class FollowSubPageScreenSideEffect {
    data class Loading(val hasData: Boolean) : FollowSubPageScreenSideEffect()
    data object LoadingMore : FollowSubPageScreenSideEffect()
    data class ShowToast(val message: String) : FollowSubPageScreenSideEffect()
    data class NavigateTo(val path: String) : FollowSubPageScreenSideEffect()
}

