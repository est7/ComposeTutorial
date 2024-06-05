package com.example.composetutorial.presentation.viewmodel

import android.util.Log
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
class MainViewModel(val handle: SavedStateHandle, private val getUserInfoListUseCase: GetUserInfoListUseCase) :
    ViewModel() {
    private val _mainScreenUiState = MutableSharedFlow<MainScreenUiState>()
    val mainScreenUiState: StateFlow<MainScreenUiState> =
        _mainScreenUiState.stateIn(viewModelScope, SharingStarted.Eagerly, MainScreenUiState.Initial)

    private val _sideEffect: Channel<MainScreenSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    suspend fun getComposeTipsList(): StateFlow<MainScreenUiState> {
        viewModelScope.launch {
            postSideEffect(MainScreenSideEffect.ShowToast("加载中..."))

            getUserInfoListUseCase("token").collect { result ->
                result.fold(onSuccess = {
                    _mainScreenUiState.emit(MainScreenUiState.Success(it))
                }, onFailure = {
                    _mainScreenUiState.emit(MainScreenUiState.Error(it.message ?: "未知错误"))
                })
            }
        }
        return mainScreenUiState
    }


    private fun postSideEffect(mainScreenUiState: MainScreenSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(mainScreenUiState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("lilili", "MainViewModel-onCleared")
    }
}

sealed class MainScreenUiState {
    data object Initial : MainScreenUiState()
    data object Loading : MainScreenUiState()
    data class Success(val composeTipsList: List<ComposeTipsItemDTO>) : MainScreenUiState()
    data class Error(val message: String) : MainScreenUiState()
}

sealed class MainScreenSideEffect {
    data class ShowToast(val message: String) : MainScreenSideEffect()
    data class NavigateTo(val path: String) : MainScreenSideEffect()
}