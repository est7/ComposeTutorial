package com.example.composetutorial.presentation.feature.tips_06

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun Tips06Screen(tips06ViewModel: Tips06ViewModel = koinViewModel()) {
    val stateInCounter by tips06ViewModel.stateInCounter.collectAsState()
    val asStateCounter by tips06ViewModel.asStateCounter.collectAsState()
    val stateInCounterWithLifecycle by tips06ViewModel.stateInCounter.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        tips06ViewModel.startCounter()
    }

    Log.d("Tips06Screen", "stateInCounter: $stateInCounter")
    Log.d("Tips06Screen", "asStateCounter: $asStateCounter")
    Log.d("Tips06Screen", "stateInCounterWithLifecycle: $stateInCounterWithLifecycle")
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "StateInCounter: $stateInCounter")
            Text(text = "AsStateCounter: $asStateCounter")
            Text(text = "StateInCounterWithLifecycle: $stateInCounterWithLifecycle")
        }

    }
}
