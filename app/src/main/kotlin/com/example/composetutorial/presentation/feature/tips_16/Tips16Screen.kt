package com.example.composetutorial.presentation.feature.tips_16

import android.widget.Toast
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content

@Composable
fun Tips16Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val context = LocalContext.current

        // bad code
        /*
                VerticalScrollContainer(content = {
                    for (i in 1..100) {
                        Text(
                            text = "Item $i", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp)
                        )
                    }
                }) {
                    // 根本不懂是什么
                    Toast.makeText(context, "Scroll delta: $it", Toast.LENGTH_SHORT).show()
                }
        */

        VerticalScrollContainer(content = {
            for (i in 1..100) {
                Text(
                    text = "Item $i", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp)
                )
            }
        }, onScroll = { delta ->
            Toast.makeText(context, "Scroll delta: $delta", Toast.LENGTH_SHORT).show()
        })

    }
}


@Composable
fun VerticalScrollContainer(
    content: @Composable () -> Unit, onScroll: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState) {
        scrollState.interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is DragInteraction.Stop -> {
                    onScroll(scrollState.value)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        content()
    }
}