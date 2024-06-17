package com.example.composetutorial.presentation.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.presentation.usecase.GetFollowSubListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import org.koin.core.module.plus

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
class SubFollowViewModel(
    val handle: SavedStateHandle,
    private val getFollowSubListUseCase: GetFollowSubListUseCase,
) : ViewModel() {

    private var list: List<ComposeTipsItemDTO> = immutableListOf()
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
                if (list.isNotEmpty()) {
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
        val oldList = list
        val isRefreshing = isRefresh
        val isLoadingMore = !isRefresh

        viewModelScope.launch {
            if (isRefreshing) {
                _subFollowScreenUiState.emit(
                    FollowSubPageScreenUiState.Loaded(
                        data = oldList,
                        listState = ListLoadState.GettingRefreshing,
                    )
                )
            } else {
                _subFollowScreenUiState.emit(
                    FollowSubPageScreenUiState.Loaded(
                        data = oldList,
                        listState = ListLoadState.GettingLoadingMore,
                    )
                )
            }

            getFollowSubListUseCase(type, page).collect { result ->

                result.fold(onSuccess = { data ->
                    if (oldList.isEmpty() && data.isEmpty()) {
                        _subFollowScreenUiState.emit(
                            FollowSubPageScreenUiState.Empty
                        )
                        return@collect
                    }

                    val noMoreData = data.size < PAGE_SIZE

                    if (isRefreshing) {
                        if (noMoreData) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = oldList, listState = ListLoadState.RefreshingNoMoreData
                                )
                            )
                        } else {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = data, listState = ListLoadState.RefreshingSuccess
                                )
                            )
                            list = data
                        }
                    }

                    if (isLoadingMore) {
                        val newList = if (oldList.isEmpty()) data else oldList + data
                        if (noMoreData) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = newList, listState = ListLoadState.LoadingMoreNoMoreData
                                )
                            )
                        } else {
                            currentPage = page
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = newList, listState = ListLoadState.LoadingMoreSuccess
                                )
                            )
                            list = newList
                        }
                    }


                }, onFailure = { exception ->
                    if (oldList.isEmpty()) {
                        _subFollowScreenUiState.emit(
                            FollowSubPageScreenUiState.LoadFailed(
                                exception.message ?: "Unknown error"
                            )
                        )
                    } else {
                        if (isRefreshing) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = oldList,
                                    listState = ListLoadState.RefreshingLoadFailed(exception.message ?: "Unknown error")
                                )
                            )
                        }

                        if (isLoadingMore) {
                            _subFollowScreenUiState.emit(
                                FollowSubPageScreenUiState.Loaded(
                                    data = oldList, listState = ListLoadState.LoadingMoreLoadFailed(
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
    data class Loaded(val listState: ListLoadState, val data: List<ComposeTipsItemDTO>) : FollowSubPageScreenUiState()
    data object Empty : FollowSubPageScreenUiState()
    data class LoadFailed(val message: String) : FollowSubPageScreenUiState()
}

/*
data class ListState(
    val isRefreshing: Boolean,
    val isLoadingMore: Boolean,
    val noMoreData: Boolean,
    val loadFailed: Boolean,
    val errorMessage: String?,
)
*/



sealed class FollowSubPageScreenSideEffect {
    data class Loading(val hasData: Boolean) : FollowSubPageScreenSideEffect()
    data object LoadingMore : FollowSubPageScreenSideEffect()
    data class ShowToast(val message: String) : FollowSubPageScreenSideEffect()
    data class NavigateTo(val path: String) : FollowSubPageScreenSideEffect()
}

