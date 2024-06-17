package com.example.composetutorial.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
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

    private var preList: ImmutableList<ComposeTipsItemDTO> = persistentListOf()
    private var currentPage = 1

    private val _subFollowScreenUiState = MutableSharedFlow<FollowSubPageScreenUiState>()
    val subFollowScreenUiState: StateFlow<FollowSubPageScreenUiState> =
        _subFollowScreenUiState.stateIn(viewModelScope, SharingStarted.Eagerly, FollowSubPageScreenUiState.Initial)

    private val _sideEffect: Channel<FollowSubPageScreenSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun refreshSubFollowPageList(type: String) {
        currentPage = 1
        updateUiState(isRefresh = true)
        fetchData(type, page = currentPage, isRefresh = true)
    }

    fun loadMoreFollowSubPageList(type: String) {
        updateUiState(isRefresh = false)
        fetchData(type, currentPage + 1, isRefresh = false)
    }

    private fun updateUiState(isRefresh: Boolean) {
        viewModelScope.launch {
            if (isRefresh) {
                if (preList.isNotEmpty()) {
                    _sideEffect.trySend(FollowSubPageScreenSideEffect.Loading(hasData = true))
                } else {
                    _sideEffect.trySend(FollowSubPageScreenSideEffect.Loading(hasData = false))
                }
            } else {
                _sideEffect.trySend(FollowSubPageScreenSideEffect.LoadingMore)
            }
        }
    }

    private fun fetchData(type: String, page: Int, isRefresh: Boolean) {
        val isRefreshing = isRefresh
        val isLoadingMore = !isRefresh

        viewModelScope.launch {
            if (isRefreshing) {
                if (preList.isEmpty()) {
                    _subFollowScreenUiState.emit(FollowSubPageScreenUiState.EmptyLoading)
                } else {
                    _subFollowScreenUiState.emit(
                        FollowSubPageScreenUiState.Loaded(
                            data = preList,
                            listState = ListLoadState.GettingRefreshing,
                        )
                    )
                }
            } else {
                _subFollowScreenUiState.emit(
                    FollowSubPageScreenUiState.Loaded(
                        data = preList,
                        listState = ListLoadState.GettingLoadingMore,
                    )
                )
            }

            getFollowSubListUseCase(type, page).collect { result ->
                result.fold(onSuccess = { data ->
                    if (preList.isEmpty() && data.isEmpty()) {
                        _subFollowScreenUiState.emit(
                            FollowSubPageScreenUiState.Empty
                        )
                        return@collect
                    }

                    val noMoreData = data.size < PAGE_SIZE

                    if (isRefreshing) {
                        preList = data
                        if (noMoreData) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = preList, listState = ListLoadState.RefreshingNoMoreData
                                )
                            )
                        } else {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = preList, listState = ListLoadState.RefreshingSuccess
                                )
                            )
                        }
                    }

                    if (isLoadingMore) {
                        preList = if (preList.isEmpty()) data else (preList + data).toImmutableList()
                        if (noMoreData) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = preList, listState = ListLoadState.LoadingMoreNoMoreData
                                )
                            )
                        } else {
                            currentPage = page
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = preList, listState = ListLoadState.LoadingMoreSuccess
                                )
                            )
                        }
                    }


                }, onFailure = { exception ->
                    if (preList.isEmpty()) {
                        _subFollowScreenUiState.emit(
                            FollowSubPageScreenUiState.LoadFailed(
                                exception.message ?: "Unknown error"
                            )
                        )
                    } else {
                        if (isRefreshing) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = preList,
                                    listState = ListLoadState.RefreshingLoadFailed(exception.message ?: "Unknown error")
                                )
                            )
                        }

                        if (isLoadingMore) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = preList, listState = ListLoadState.LoadingMoreLoadFailed(
                                        exception.message ?: "Unknown error"
                                    )
                                )
                            )
                        }
                    }

                    postSideEffect(FollowSubPageScreenSideEffect.ShowToast(exception.message ?: "Unknown error"))
                })
            }
        }
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

