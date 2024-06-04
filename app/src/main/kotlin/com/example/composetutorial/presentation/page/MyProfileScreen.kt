package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import com.example.composetutorial.presentation.viewmodel.MyProfileScreenUiState
import com.example.composetutorial.uiwidget.EmptyContent
import com.example.composetutorial.uiwidget.LoadingContent
import com.example.composetutorial.utils.HandleMyProfileSideEffect
import org.koin.androidx.compose.koinViewModel


@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier, viewModel: LoginViewModel = koinViewModel()
) {
    val uiState = viewModel.myProfileScreenUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.updateMyProfile()
    }
    HandleMyProfileSideEffect(viewModel)
    MyProfileScreen(uiState.value, modifier)
}


@Composable
internal fun MyProfileScreen(
    state: MyProfileScreenUiState, modifier: Modifier = Modifier
) {

    when (state) {
        is MyProfileScreenUiState.Error -> {
            EmptyContent(messageRes = state.message, modifier = modifier)
        }

        MyProfileScreenUiState.Initial -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "MyProfileScreen Initial")
            }
        }

        MyProfileScreenUiState.Loading -> {
            LoadingContent()
        }

        is MyProfileScreenUiState.Success -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = state.myProfile.name)
            }

        }
    }
}

@Preview(name = "MyProfileScreen")
@Composable
private fun PreviewMyProfileScreen() {
    MyProfileScreen()
}