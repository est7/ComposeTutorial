package com.example.composetutorial.presentation.feature.tips_22

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun Tips22Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Tips22BadDataScreen()
            HorizontalDivider()
            Tips22GoodDataScreen()
        }
    }
}


@Composable
fun Tips22BadDataScreen() {
    var data by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        data = fetchData()
    }
    if (data == null) {
        CircularProgressIndicator()
    } else {
        Text(text = data!!)
    }
}

suspend fun fetchData(): String {
    delay(2000)
    return "Hello, World!"
}


@Composable
fun Tips22GoodDataScreen() {
    val data by produceState<String?>(initialValue = null) {
        value = fetchData()
    }
    if (data == null) {
        CircularProgressIndicator()
    } else {
        Text(text = data!!)
    }
}