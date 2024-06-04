package com.example.composetutorial.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.presentation.usecase.LoginUseCase
import com.example.composetutorial.presentation.usecase.LogoutUseCase
import com.example.composetutorial.presentation.usecase.UpdateMyInfoUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    val handle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateMyInfoUseCase: UpdateMyInfoUseCase
) : ViewModel() {

    suspend fun login() {
        viewModelScope.launch {
            val success = loginUseCase("username", "password")
        }
    }

    suspend fun logout() {
        logoutUseCase.invoke()
    }

    suspend fun updateMyInfo() {
        updateMyInfoUseCase.invoke("token")
    }
}
