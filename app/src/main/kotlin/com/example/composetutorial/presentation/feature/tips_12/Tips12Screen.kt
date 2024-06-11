package com.example.composetutorial.presentation.feature.tips_12

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Tips12Screen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val showToast = {
            val message = "Options clicked"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        Column() {
            BadOptionsButton(onClick = showToast)
            GoodOptionsButton(onClick = showToast)
            LargeClickableButton(onClick = showToast)
            ButtonWithLargerClickableArea(onClick = showToast)
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