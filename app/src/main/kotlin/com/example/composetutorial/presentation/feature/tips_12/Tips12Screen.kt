package com.example.composetutorial.presentation.feature.tips_12

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.presentation.feature.tips_18.GoodListScreen

@Composable
fun Tips12Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column() {
            BadOptionsButton(onClick = {})
            GoodOptionsButton(onClick = {})
            LargeClickableButton(onClick = {})
            ButtonWithLargerClickableArea(onClick = {})
        }
    }
}

@Preview
@Composable
fun Tips12ScreenPreview() {
    Tips12Screen()
}

@Composable
fun BadOptionsButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Options", modifier = modifier.clickable {
        onClick()
    })
}

@Composable
fun GoodOptionsButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick, modifier = modifier,

        ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Options",
        )

    }
}

@Composable
fun LargeClickableButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .padding(16.dp) // 为 Button 添加内边距
            .size(200.dp, 80.dp) // 设置 Button 的大小
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Options",
        )
    }
}

/**
 * 使用pointerInput设置足够大的点击区域的 Button
 */
@Composable
fun ButtonWithLargerClickableArea(onClick: () -> Unit) {
    Box(modifier = Modifier
        .size(200.dp) // 设置 Box 的大小为点击区域的大小
        .pointerInput(Unit) {
            detectTapGestures(onTap = { onClick() })
        }) {
        Button(
            onClick = onClick, modifier = Modifier.size(100.dp) // 设置 Button 的实际显示大小
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Options",
            )

        }
    }
}