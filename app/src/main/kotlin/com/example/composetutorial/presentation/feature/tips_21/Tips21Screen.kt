package com.example.composetutorial.presentation.feature.tips_21

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Tips21Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            TipsBadNoAnimateComponent()
            TipsGoodAnimateComponent()
        }
    }
}


@Composable
fun TipsBadNoAnimateComponent() {
    var enabled by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { enabled = !enabled }) {
            Text("Toggle")
        }
        Box(
            modifier = Modifier
                .size(if (enabled) 100.dp else 50.dp)
                .background(Color.Red, CircleShape)
        )
    }
}

@Composable
fun TipsGoodAnimateComponent() {
    var enabled by remember { mutableStateOf(false) }
    val size by animateDpAsState(targetValue = if (enabled) 100.dp else 50.dp, label = "")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { enabled = !enabled }) {
            Text("Toggle")
        }
        Box(
            modifier = Modifier
                .size(size)
                .background(Color.Red, CircleShape)
        )
    }
}