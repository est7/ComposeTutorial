package com.example.composetutorial.presentation.feature.tips_17

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.UserInfoDTO
import com.example.composetutorial.presentation.viewmodel.MainScreenSideEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 *
 * @author: est8
 * @date: 2024/6/7
 */
class Tips17ViewModel(val handle: SavedStateHandle) : ViewModel() {
    private val _tips17UiState = MutableStateFlow<Tips17UiState>(Tips17UiState.Initial)
    val tips17UiState = _tips17UiState.asStateFlow()


    private val _tips17SideEffect: Channel<Tips17SideEffect> = Channel()
    val tips17SideEffect = _tips17SideEffect.receiveAsFlow()



    fun login(username: String, password: String) {
        // 随机失败 50% 的概率
        viewModelScope.launch {
            _tips17UiState.value = Tips17UiState.Loading
            delay(2000)
            val random = (0..1).random()
            if (random == 0) {
                _tips17UiState.value = Tips17UiState.Error("login failed")
            } else {
                val userInfo = UserInfoDTO("nick", 18, "", "")
                _tips17UiState.value = Tips17UiState.Success(userInfo)
                // 带参数 name = userInfo.name
                _tips17SideEffect.send(Tips17SideEffect.Navigate("tips_17a", userInfo))
            }
        }
    }



    private val _tips17SuspendUiState = MutableStateFlow<Tips17UiState>(Tips17UiState.Initial)
    val tips17SuspendUiState = _tips17SuspendUiState.asStateFlow()

    private val _tips17SuspendSideEffect: Channel<Tips17SideEffect> = Channel()
    val tips17SuspendSideEffect = _tips17SuspendSideEffect.receiveAsFlow()

    suspend fun loginSuspend(username: String, password: String) {
        _tips17SuspendUiState.value = Tips17UiState.Loading
        delay(2000)
        val random = (0..1).random()
        if (random == 0) {
            _tips17SuspendUiState.value = Tips17UiState.Error("login failed")
        } else {
            val userInfo = UserInfoDTO("nick", 18, "", "")
            _tips17SuspendUiState.value = Tips17UiState.Success(userInfo)
            _tips17SuspendSideEffect.trySend(Tips17SideEffect.Navigate("tips_17a", userInfo))
        }
    }
}

sealed class Tips17UiState {
    data object Initial : Tips17UiState()
    data object Loading : Tips17UiState()
    data class Success(val data: UserInfoDTO) : Tips17UiState()
    data class Error(val error: String) : Tips17UiState()
}

sealed class Tips17Action {
    data class Login(val username: String, val password: String) : Tips17Action()
}

sealed class Tips17SideEffect {
    data class ShowToast(val message: String) : Tips17SideEffect()
    data class Navigate(val route: String, val userInfo: UserInfoDTO) : Tips17SideEffect()
}