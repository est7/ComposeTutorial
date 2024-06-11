package com.example.composetutorial.presentation.feature.tips_23

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Tips23Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            Tips23DerivedStateExample()
            HorizontalDivider()
            Tips23GoodDerivedStateExample()
        }
    }
}

@Composable
fun Tips23DerivedStateExample() {
    var count by remember { mutableIntStateOf(0) }
    val isEven = count % 2 == 0

    Column {
        Button(onClick = { count++ }) {
            Text("Increment")
        }
        Text("Count is: $count")
        if (isEven) {
            Text("Count is even")
        } else {
            Text("Count is odd")
        }
    }
}

@Composable
fun Tips23GoodDerivedStateExample() {
    var count by remember { mutableIntStateOf(0) }
    val isEven by remember {
        derivedStateOf { count % 2 == 0 }
    }

    Column {
        Button(onClick = { count++ }) {
            Text("Increment")
        }
        Text("Count is: $count")
        if (isEven) {
            Text("Count is even")
        } else {
            Text("Count is odd")
        }
    }
}