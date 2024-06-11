package com.example.composetutorial.presentation.feature.tips_09

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun Tips09Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Row {
                Tips09BadMyButton(onClick = {})
                Tips09BadMyButton(onClick = {})
            }
            Row {
                Tips09GoodMyButton(onClick = {})
                Tips09GoodMyButton(onClick = {})
            }
            Row {
                Tips09GoodMyButton(onClick = {}, modifier = Modifier.weight(1f))
                Tips09GoodMyButton(onClick = {}, modifier = Modifier.weight(1f))
            }

            Row {
                Tips09GoodMyButton(onClick = {}, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun Tips09BadMyButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, modifier = modifier
            .clip(RoundedCornerShape(100))
            .fillMaxWidth()
    ) {
        Text(text = "Cool button")
    }
}

@Composable
fun Tips09GoodMyButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, modifier = modifier.clip(RoundedCornerShape(100))
    ) {
        Text(text = "Cool button")
    }
}