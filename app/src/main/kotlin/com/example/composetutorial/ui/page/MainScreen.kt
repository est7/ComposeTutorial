package com.example.composetutorial.ui.page

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.composetutorial.MainViewModel

/**
 *
 * @author: summer
 * @date: 2024/5/30
 */

@Composable
fun MainScreen(viewModel: MainViewModel) {
    LaunchedEffect{

    }

    LazyColumn {
        items(100) {
            Text("Item $it")
        }
    }
}