package com.example.composetutorial.presentation.feature.tips_07

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun Tips07Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            BadRotatingBox()
            GoodRotatingBox()
        }
    }
}

@Composable
fun BadRotatingBox() {
    val transition = rememberInfiniteTransition(label = "")
    val rotationRatio by transition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(200)
        ), label = ""
    )

    Box(
        modifier = Modifier
            .rotate(rotationRatio * 360f)
            .size(100.dp)
            .background(Color.Red)
    )
}

@Composable
fun GoodRotatingBox() {
    val transition = rememberInfiniteTransition(label = "")
    val rotationRatio by transition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(200)
        ), label = ""
    )

    Box(modifier = Modifier
        .graphicsLayer {
            rotationZ = rotationRatio * 360f
        }
        .size(100.dp)
        .background(Color.Red))
}
