package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyUserInfoScreen(
    modifier: Modifier = Modifier
) {
    // 居中
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "MyUserInfoScreen", modifier = modifier.align(Alignment.Center))
    }
}

@Preview(name = "MyUserInfoScreen")
@Composable
private fun PreviewMyUserInfoScreen() {
    MyUserInfoScreen()
}