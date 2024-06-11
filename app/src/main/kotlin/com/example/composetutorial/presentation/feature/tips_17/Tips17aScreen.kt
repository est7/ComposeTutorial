package com.example.composetutorial.presentation.feature.tips_17

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composetutorial.navagation.LocalNavController

@Composable
fun Tips17aScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val navController = LocalNavController.current
        val name = remember { mutableStateOf<String?>(null) }

        LaunchedEffect(navController.currentBackStackEntry) {
            name.value = navController.currentBackStackEntry?.arguments?.getString("name")
        }


        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            Text("Welcome to MyProfilePage")
            if (name.value != null) {
                Text("My name is:$name")
            }
        }
    }
}
