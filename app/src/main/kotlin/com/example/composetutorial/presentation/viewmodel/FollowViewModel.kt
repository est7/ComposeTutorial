package com.example.composetutorial.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.presentation.usecase.GetFollowListConfigUseCase
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
) :
    ViewModel() {
    private val _followScreenUiState = MutableSharedFlow<FollowScreenUiState>()
    val followScreenUiState: StateFlow<FollowScreenUiState> =
        _followScreenUiState.stateIn(viewModelScope, SharingStarted.Eagerly, FollowScreenUiState.Initial)

    private val _sideEffect: Channel<FollowPageScreenSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()



    fun getFollowConfig(){
        viewModelScope.launch {
            postSideEffect(FollowPageScreenSideEffect.ShowToast("加载中..."))
            getFollowListConfigUseCase().collect { result ->
                result.fold(onSuccess = {
                    _followScreenUiState.emit(FollowScreenUiState.Success(it))
                }, onFailure = {
                    _followScreenUiState.emit(FollowScreenUiState.Error(it.message ?: "未知错误"))
                })
            }
        }
    }




    private fun postSideEffect(followScreenUiState: FollowPageScreenSideEffect) {
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

sealed class FollowPageScreenSideEffect {
    data class ShowToast(val message: String) : FollowPageScreenSideEffect()
    data class NavigateTo(val path: String) : FollowPageScreenSideEffect()
}
