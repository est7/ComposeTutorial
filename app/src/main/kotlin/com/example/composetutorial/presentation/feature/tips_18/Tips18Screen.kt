package com.example.composetutorial.presentation.feature.tips_18

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
            horizontalArrangement = Arrangement.SpaceEvenly
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
            .background(color = androidx.compose.ui.graphics.Color.Red)
            .widthIn(100.dp)
            .verticalScroll(scrollState)
    ) {
        for (i in 1..50) {
            // 每次 scrollState.value 发生变化时，alpha 值都会重新计算，并且 BadListItem 会被重新执行。
            BadListItem(count = i, alpha = scrollState.value / 50f)
        }
    }
}

@Composable
fun GoodListScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(color = androidx.compose.ui.graphics.Color.Green)
            .widthIn(100.dp)
            .verticalScroll(scrollState)
    ) {
        for (i in 1..50) {
            //当 scrollState.value 发生变化时，lambda 表达式本身的引用没有改变，
            // Compose 不会因为这个原因重新执行 GoodListItem。
            // 只有在 UI 更新时，alpha 的值才会重新计算，并应用到 Modifier.graphicsLayer 中。
            GoodListItem(count = i, alpha = { scrollState.value / 50f })
        }
    }
}

@Composable
fun BadListItem(
    count: Int, alpha: Float, modifier: Modifier = Modifier
) {
    Text(text = "List item$count", fontSize = 20.sp, modifier = modifier
        .padding(16.dp)
        .graphicsLayer {
            this.alpha = alpha
        })
}

// 通过将 alpha 作为 lambda 传递，我们避免了每次滚动时都重新组合 ListItem，从而提高了性能。
@Composable
fun GoodListItem(
    count: Int, alpha: () -> Float, modifier: Modifier = Modifier
) {
    Text(text = "List item$count", fontSize = 20.sp, modifier = modifier
        .padding(16.dp)
        .graphicsLayer {
            this.alpha = alpha()
        })
}