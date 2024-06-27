package com.example.composetutorial.presentation.feature.tips_28

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.koinViewModel


@Composable
fun Tips28Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Tips28Content()
    }
}

@Composable
fun Tips28Content(tips28ViewModel: Tips28ViewModel = koinViewModel()) {

}
