package com.example.composetutorial.presentation.feature.tips_18

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Tips18Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start, modifier = Modifier
        ) {
            BadListScreen()
            VerticalDivider()
            GoodListScreen()
        }
    }
}

@Composable
fun BadListScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        for (i in 1..50) {
            BadListItem(
                alpha = scrollState.value / 50f, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun GoodListScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        for (i in 1..50) {
            GoodListItem(
                alpha = { scrollState.value / 50f }, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BadListItem(
    alpha: Float, modifier: Modifier = Modifier
) {
    Text(text = "List item", fontSize = 20.sp, modifier = modifier
        .padding(16.dp)
        .graphicsLayer {
            this.alpha = alpha
        })
}

// 通过将 alpha 作为 lambda 传递，我们避免了每次滚动时都重新组合 ListItem，从而提高了性能。
@Composable
fun GoodListItem(
    alpha: () -> Float, modifier: Modifier = Modifier
) {
    Text(text = "List item", fontSize = 20.sp, modifier = modifier
        .padding(16.dp)
        .graphicsLayer {
            this.alpha = alpha()
        })
}