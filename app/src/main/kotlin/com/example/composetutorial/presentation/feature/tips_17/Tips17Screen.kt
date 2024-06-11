package com.example.composetutorial.presentation.feature.tips_17

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.Navigator
import com.example.composetutorial.navagation.LocalNavController
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun Tips17Screen(videModel: Tips17ViewModel = koinViewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            BadTips17Screen(videModel)
            HorizontalDivider()
            GoodTips17Screen(videModel)

        }
    }
}


@Composable
private fun BadTips17Screen(videModel: Tips17ViewModel) {
    val context = LocalNavController.current.context
    val navController = LocalNavController.current
    val state by videModel.tips17UiState.collectAsStateWithLifecycle()
    val sideEffect by videModel.tips17SideEffect.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        sideEffect?.let {
            when (it) {
                is Tips17SideEffect.Navigate -> {
                    navController.navigate(it.route)
                }

                is Tips17SideEffect.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    Tips17Screen(state = state, onAction = {
        when (it) {
            is Tips17Action.Login -> {
                // 通过点击按钮触发登录，不应该使用 suspend
                scope.launch {
                    videModel.loginSuspend(it.username, it.password)
                }
            }
        }
    })
}


@Composable
private fun GoodTips17Screen(videModel: Tips17ViewModel) {
    val context = LocalNavController.current.context
    val navController = LocalNavController.current
    val state by videModel.tips17UiState.collectAsStateWithLifecycle()
    val sideEffect by videModel.tips17SideEffect.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        sideEffect?.let {
            when (it) {
                is Tips17SideEffect.Navigate -> {
                    // 带参数 it.userInfo.name
                    navController.navigate("tips17aScreen/${it.userInfo.name}")
                }

                is Tips17SideEffect.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    Tips17Screen(state = state, onAction = {
        when (it) {
            is Tips17Action.Login -> {
                // 通过点击按钮触发登录，不应该使用 suspend
                videModel.login(it.username, it.password)
            }
        }
    })
}


@Composable
fun Tips17Screen(state: Tips17UiState, onAction: (Tips17Action) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top, modifier = Modifier
    ) {
        when (state) {
            Tips17UiState.Loading -> {
                Text(text = "Loading")
            }

            is Tips17UiState.Success -> {
                // 成功后直接跳入下一个页面
            }

            is Tips17UiState.Error -> {
                Text(text = "Error")
            }

            Tips17UiState.Initial -> {
                Text(text = "Hello World!,welcome to Jetpack Compose,Please click the button to Login")
                Button(onClick = {
                    onAction(Tips17Action.Login("username", "password"))
                }) {
                    Text(text = "Login")
                }
            }
        }
    }

}

@Preview
@Composable
fun Tips17ScreenPreview(state: Tips17UiState = Tips17UiState.Loading) {
    Tips17Screen(state = state, onAction = {})
}
