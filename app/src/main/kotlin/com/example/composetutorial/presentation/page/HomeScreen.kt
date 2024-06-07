package com.example.composetutorial.presentation.page

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.navagation.LocalNavController
import com.example.composetutorial.presentation.viewmodel.MainScreenUiState
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import com.example.composetutorial.uiwidget.EmptyContent
import com.example.composetutorial.uiwidget.LoadingContent
import com.example.composetutorial.utils.HandleMainSideEffect
import com.example.composetutorial.utils.navigateTo
import kotlinx.coroutines.cancel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val uiState = viewModel.mainScreenUiState.collectAsStateWithLifecycle()
    HandleMainSideEffect(viewModel.sideEffect)
    LaunchedEffect(Unit) {
        viewModel.getComposeTipsList()
    }
    DisposableEffect(key1 = viewModel) {
        onDispose {
            Log.d("lilili", "HomeScreen-viewModel.viewModelScope.cancel()")
            viewModel.viewModelScope.cancel()
        }
    }

    HomeScreen(uiState = uiState.value, modifier)
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
            SuccessContent(uiState)

        }
    }


}

@Composable
private fun SuccessContent(uiState: MainScreenUiState.Success) {
    val navController = LocalNavController.current
    LazyColumn {
        items(uiState.composeTipsList) { composeTip ->
            ComposeTipItem(composeTip) {
                navController.navigateTo(composeTip.path)
            }
        }
    }
}

@Composable
fun ComposeTipItem(composeTip: ComposeTipsItemDTO, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxSize().padding(8.dp), onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(0.dp, 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = composeTip.path)
                Text(text = composeTip.id)
            }
            Text(text = composeTip.desc, modifier = Modifier.fillMaxSize().padding(0.dp, 8.dp))
        }

    }
}


@Preview(name = "Home")
@Composable
private fun PreviewHome() {
    HomeScreen(
        uiState = MainScreenUiState.Success(listOf(ComposeTipsItemDTO("1", "path", "desc"))),
        modifier = Modifier.fillMaxSize()
    )
}

