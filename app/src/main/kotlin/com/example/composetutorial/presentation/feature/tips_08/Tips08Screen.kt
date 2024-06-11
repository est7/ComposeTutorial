package com.example.composetutorial.presentation.feature.tips_08

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetutorial.data.dto.UserInfoDTO
import com.example.composetutorial.presentation.viewmodel.LoginEvent
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import com.example.composetutorial.presentation.viewmodel.MyProfileScreenUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun Tips08Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            BadLoginScreen()
            HorizontalDivider()
            GoodLoginScreenRoot()
        }

    }
}


@Composable
fun BadLoginScreen() {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.myProfileScreenUiState.collectAsStateWithLifecycle()
    Column {
        when (state) {
            is MyProfileScreenUiState.Error -> {
                Text(text = (state as MyProfileScreenUiState.Error).message)
            }

            MyProfileScreenUiState.Initial -> {
                Button(onClick = { viewModel.login() }) {
                    Text(text = "Login")
                }
            }

            MyProfileScreenUiState.Loading -> {
                CircularProgressIndicator()
            }

            is MyProfileScreenUiState.Success -> {
                Text(text = (state as MyProfileScreenUiState.Success).myProfile.name)
            }

        }
    }
}


@Composable
fun GoodLoginScreenRoot() {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.myProfileScreenUiState.collectAsStateWithLifecycle()

    GoodLoginScreen(state, viewModel::onEvent)
}


@Composable
fun GoodLoginScreen(state: MyProfileScreenUiState, onEvent: (LoginEvent) -> Unit) {
    Column {
        when (state) {
            is MyProfileScreenUiState.Error -> {
                Text(text = state.message)
            }

            MyProfileScreenUiState.Initial -> {
                Button(onClick = { onEvent(LoginEvent.OnLoginClicked) }) {
                    Text(text = "Login")
                }
            }

            MyProfileScreenUiState.Loading -> {
                CircularProgressIndicator()
            }

            is MyProfileScreenUiState.Success -> {
                Text(text = state.myProfile.name)
            }

        }
    }
}

@Preview
@Composable
fun PreviewLoginInitial08Screen() {
    GoodLoginScreen(MyProfileScreenUiState.Initial, {})
}

@Preview
@Composable
fun PreviewLoginLoading08Screen() {
    GoodLoginScreen(MyProfileScreenUiState.Loading, {})
}

@Preview
@Composable
fun PreviewLoginError08Screen() {
    GoodLoginScreen(MyProfileScreenUiState.Error("error message"), {})
}

@Preview
@Composable
fun PreviewLoginSuccess08Screen() {
    GoodLoginScreen(MyProfileScreenUiState.Success(UserInfoDTO("nick", 17, "https://avatar.com/1.jpg", "desc")), {})
}

