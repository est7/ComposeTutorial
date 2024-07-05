package com.example.composetutorial.presentation.feature.tips_28

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.eventFlow
import org.koin.androidx.compose.koinViewModel


@Composable
fun Tips28Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green), contentAlignment = Alignment.Center
    ) {
        Tips28Content()
    }
}

@Composable
fun Tips28Content(tips28ViewModel: Tips28ViewModel = koinViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var lifecycleState: String by remember { mutableStateOf("Lifecycle state: ${lifecycleOwner.lifecycle.currentState}") }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.eventFlow.collect {
            when (it) {
                Lifecycle.Event.ON_CREATE -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"
                }

                Lifecycle.Event.ON_START -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"
                }

                Lifecycle.Event.ON_RESUME -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"

                }

                Lifecycle.Event.ON_PAUSE -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"
                }

                Lifecycle.Event.ON_STOP -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"
                }

                Lifecycle.Event.ON_DESTROY -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"
                }

                Lifecycle.Event.ON_ANY -> {
                    lifecycleState = "Lifecycle state: ${lifecycleOwner.lifecycle.currentState}"
                }
            }
        }
    }

    Text(text = lifecycleState, color = Color.White)

}
