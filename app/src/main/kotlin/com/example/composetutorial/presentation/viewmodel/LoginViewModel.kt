package com.example.composetutorial.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.UserInfoDTO
import com.example.composetutorial.presentation.usecase.LoginUseCase
import com.example.composetutorial.presentation.usecase.LogoutUseCase
import com.example.composetutorial.presentation.usecase.UpdateMyInfoUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    val handle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateMyInfoUseCase: UpdateMyInfoUseCase
) : ViewModel() {
    private val _myProfileScreenUiState = MutableSharedFlow<MyProfileScreenUiState>()
    val myProfileScreenUiState: StateFlow<MyProfileScreenUiState> =
        _myProfileScreenUiState.stateIn(viewModelScope, SharingStarted.Eagerly, MyProfileScreenUiState.Initial)

    private val _sideEffect: Channel<MyProfileSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()


    suspend fun login() {
        viewModelScope.launch {
            val success = loginUseCase("username", "password")
        }
    }

    suspend fun logout() {
        logoutUseCase.invoke()
    }

    suspend fun updateMyProfile() {
        viewModelScope.launch {
            _sideEffect.send(MyProfileSideEffect.ShowToast("updateMyProfile"))
            updateMyInfoUseCase.invoke("token").collect { it ->
                it.fold({
                    _myProfileScreenUiState.emit(MyProfileScreenUiState.Success(it))
                }, {
                    _myProfileScreenUiState.emit(MyProfileScreenUiState.Error(it.message ?: "error"))
                })

            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("lilili", "LoginViewModel-onCleared")
    }
}

sealed class MyProfileScreenUiState {
    data object Initial : MyProfileScreenUiState()
    data object Loading : MyProfileScreenUiState()
    data class Success(val myProfile: UserInfoDTO) : MyProfileScreenUiState()
    data class Error(val message: String) : MyProfileScreenUiState()
}

sealed class MyProfileSideEffect {
    data class ShowToast(val message: String) : MyProfileSideEffect()
    data class NavigateTo(val path: String) : MyProfileSideEffect()
}