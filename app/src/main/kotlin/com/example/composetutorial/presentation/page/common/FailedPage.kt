package com.example.composetutorial.presentation.page.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FailedPage(message: String, onClickRetry: () -> Unit) {
    val visible = remember { message.isNotEmpty() }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Load Failed")
        if (visible) {
            Text(text = message)
        }
        Button(onClick = onClickRetry) {
            Text(text = "Retry")
        }
    }

}