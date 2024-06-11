package com.example.composetutorial.presentation.feature.tips_24

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.navagation.LocalNavController

@Composable
fun Tips24Screen() {
    val navController = LocalNavController.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(onClick = {
            navController.navigate("tips_17")
        }) {
            Text("Navigate to Tips24Screen")
        }
    }
}
