package com.example.composetutorial.presentation.feature.tips_22

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Tips22Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        var show by remember {
            mutableStateOf(true)
        }

        Column {
            if (show) {
                Tips22BadDataScreen()
                HorizontalDivider()
                Tips22GoodDataScreen()
            }
            Button(onClick = { show = !show }) {
                Text("Toggle")
            }
        }
    }
}


//
/**
 * launcheffect 的区别就是remember
 * LaunchedEffect
 *
 * 	•	更通用：可以用于任何需要启动协程的场景，不仅限于数据加载。
 * 	•	明确的生命周期绑定：协程与 Composable 的生命周期绑定，确保在 Composable 卸载时取消协程。
 *
 * produceState
 *
 * 	•	状态绑定：异步操作的结果直接绑定到状态，简化了状态管理。
 * 	•	自动重组：状态变化会自动触发 Compose 重组，无需手动管理状态更新。
 * 	•	生命周期管理：内部处理生命周期管理，确保异步操作在 Composable 卸载时被取消。
 */
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

@Composable
fun Tips22GoodDataScreen() {
    val data by produceState<String?>(initialValue = null) {
        value = fetchData()
        awaitDispose {
            Log.d("Tips22GoodDataScreen", "produceState-dispose")
        }
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
fun Tips22DisposeDataScreen() {
    var data by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        scope.launch {
            data = fetchData()
        }

        onDispose {
            Log.d("Tips22GoodDataScreen", "DisposableEffect-dispose")
        }
    }
    if (data == null) {
        CircularProgressIndicator()
    } else {
        Text(text = data!!)
    }
}
