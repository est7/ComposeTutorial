package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Greeting()
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Andy") }
    Column {
        Name(name = name)
        Button(onClick = {
            name = if (name == "Andy") {
                "Bob"
            } else {
                "Andy"
            }
        }) {
            Text(text = "Change name")
        }
    }
}

@Composable
fun Name(name: String) {
    Text(text = "Hello $name!")
}


@Preview(name = "FollowList")
@Composable
private fun PreviewFollowList() {
    FollowScreen()
}