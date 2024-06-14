package com.example.composetutorial.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.presentation.usecase.GetUserInfoListUseCase
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
class FollowViewModel(
    val handle: SavedStateHandle,
    private val getFollowListConfigUseCase: GetFollowListConfigUseCase,
    private val getFollowSubListUseCase: GetFollowSubListUseCase,
) :
    ViewModel() {
    private val _followScreenUiState = MutableSharedFlow<FollowScreenUiState>()
    val followScreenUiState: StateFlow<FollowScreenUiState> =
        _followScreenUiState.stateIn(viewModelScope, SharingStarted.Eagerly, FollowScreenUiState.Initial)
    val followScreenUiState: StateFlow<FollowScreenUiState> =
        _followScreenUiState.stateIn(viewModelScope, SharingStarted.Eagerly, FollowScreenUiState.Initial)

    private val _sideEffect: Channel<FollowSubPageScreenSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()



    fun getFollowConfig(){
        viewModelScope.launch {
            postSideEffect(FollowSubPageScreenSideEffect.ShowToast("加载中..."))
            getFollowListConfigUseCase().collect { result ->
                result.fold(onSuccess = {
                    _followScreenUiState.emit(FollowScreenUiState.Success(it))
                }, onFailure = {
                    _followScreenUiState.emit(FollowScreenUiState.Error(it.message ?: "未知错误"))
                })
            }
        }
    }


    fun getFollowSubPageList(type: String, page: Int): StateFlow<MainScreenUiState> {
        viewModelScope.launch {
            getFollowSubListUseCase(type,page).collect { result ->
                result.fold(onSuccess = {
                    _mainScreenUiState.emit(MainScreenUiState.Success(it))
                }, onFailure = {
                    _mainScreenUiState.emit(MainScreenUiState.Error(it.message ?: "未知错误"))
                })
            }
        }
        return mainScreenUiState
    }


    private fun postSideEffect(followScreenUiState: FollowSubPageScreenSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(followScreenUiState)
        }
    }

}

sealed class FollowScreenUiState {
    data object Initial : FollowScreenUiState()
    data object Loading : FollowScreenUiState()
    data class Success(val composeTipsList: List<ComposeTipsItemDTO>) : FollowScreenUiState()
    data class Error(val message: String) : FollowScreenUiState()
}


sealed class FollowSubPageScreenUiState {
    data object Initial : FollowSubPageScreenUiState()
    data object Loading : FollowSubPageScreenUiState()
    data class Success(val composeTipsList: List<ComposeTipsItemDTO>) : FollowSubPageScreenUiState()
    data class Error(val message: String) : FollowSubPageScreenUiState()
}

sealed class FollowSubPageScreenSideEffect {
    data class ShowToast(val message: String) : FollowSubPageScreenSideEffect()
    data class NavigateTo(val path: String) : FollowSubPageScreenSideEffect()
}