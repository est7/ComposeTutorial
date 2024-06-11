package com.example.composetutorial.presentation.feature.tips_15

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Tips15Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            Column(
            ) {
                for (i in 1..50) {
                    Tips15ListItem(i)
                }

            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                for (i in 1..50) {
                    Tips15ListItem(i)
                }

            }
        }

    }
}

@Composable
fun Tips15ListItem(count: Int) {
    Text(
        text = "List item$count"
    )
}