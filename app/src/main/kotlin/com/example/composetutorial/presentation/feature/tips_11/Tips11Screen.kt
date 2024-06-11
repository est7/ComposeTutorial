package com.example.composetutorial.presentation.feature.tips_11

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Tips11Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column() {
            Button(
                onClick = {}, modifier = Modifier
                    .widthIn(400.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Button 1")
            }

            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Button 1")
            }
        }
    }
}
