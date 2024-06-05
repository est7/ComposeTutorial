package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetutorial.presentation.viewmodel.MainScreenUiState
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import com.example.composetutorial.uiwidget.EmptyContent
import com.example.composetutorial.uiwidget.LoadingContent
import com.example.composetutorial.utils.HandleMainSideEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val uiState = viewModel.mainScreenUiState.collectAsStateWithLifecycle()
    HandleMainSideEffect(viewModel = viewModel)

    LaunchedEffect(Unit) {
        viewModel.getComposeTipsList()
    }

    HomeScreen(uiState.value, modifier)
}

@Composable
fun HomeScreen(uiState: MainScreenUiState, modifier: Modifier) {

    when (uiState) {
        is MainScreenUiState.Error -> {
            EmptyContent(messageRes = uiState.message, modifier = modifier)
        }

        MainScreenUiState.Initial -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "HomeScreen Initial")
            }
        }

        MainScreenUiState.Loading -> {
            LoadingContent()
        }

        is MainScreenUiState.Success -> {
            LazyColumn {
                items(uiState.composeTipsList) { composeTip ->
                    Text(text = composeTip.desc)
                }
            }

        }
    }


}

@Preview(name = "Home")
@Composable
private fun PreviewHome() {
    HomeScreen()
}

