package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import com.example.composetutorial.utils.HandleSideEffect

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    // val viewModel: MainViewModel = viewModels()
    // HandleSideEffect(viewModel = viewModel)
    // LaunchedEffect(Unit) {
    //     viewModel.getComposeTipsList()
    // }
    LazyColumn {
        items(100) {
            Text("Item $it")
        }
    }
}

@Preview(name = "Home")
@Composable
private fun PreviewHome() {
    HomeScreen()
}

