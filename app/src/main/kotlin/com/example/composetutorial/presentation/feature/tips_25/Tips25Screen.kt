package com.example.composetutorial.presentation.feature.tips_25

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composetutorial.presentation.feature.tips_01.NoRippleInteractionSource
import kotlinx.coroutines.delay

@Composable
fun Tips25Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Tip25Button()
    }
}

@Composable
fun Tip25Button() {
    var count by remember { mutableIntStateOf(0) }
    var switch by remember { mutableStateOf(false) }

    Column {
        Calculation(input = count)

        Button(interactionSource = NoRippleInteractionSource(), onClick = { count++ }) {
            Text("Increase count: $count")
        }
        Button(interactionSource = NoRippleInteractionSource(), onClick = { switch = !switch }) {
            Text("开关")
        }
        Spacer(modifier = Modifier.height(16.dp))

        DisplayText(count, switch)
        Spacer(modifier = Modifier.height(16.dp))
        DisplayText1(count, switch)
        Spacer(modifier = Modifier.height(16.dp))
        DisplayText2(count, switch)
        Spacer(modifier = Modifier.height(16.dp))
        DisplayText3(count, switch)
    }
}


@Composable
private fun Calculation(input: Int) {
    val rememberUpdatedStateInput by rememberUpdatedState(input)
    val rememberedInput = remember { input }

    Text("updatedInput: $rememberUpdatedStateInput, rememberedInput: $rememberedInput")
}


@Composable
fun DisplayText(count: Int, checked: Boolean) {
    // remember 只在初次组合时记住了 clickHandlerToPrint 的初始状态，后续组合不会更新它。
    val clickHandlerToPrint = remember {
        {
            // 不引用 count 的值，这样 clickHandlerToPrint 不会重新组合
            // Log.d("Tips25Screen", "Button clicked $count times")
            Unit
        }
    }
    Switch(checked = checked, onCheckedChange = null)
    Button(
        interactionSource = NoRippleInteractionSource(), onClick = clickHandlerToPrint
    ) {
        Text("Click me")
    }
}


@Composable
fun DisplayText1(count: Int, checked: Boolean) {
    // remember 只在初次组合时记住了 clickHandlerToPrint 的初始状态，后续组合不会更新它。
    val clickHandlerToPrint = remember {
        {
            Log.d("Tips25Screen", "Button clicked $count times")
            Unit
        }
    }
    Switch(checked = checked, onCheckedChange = null)

    Text("$count")

    Button(
        interactionSource = NoRippleInteractionSource(), onClick = clickHandlerToPrint
    ) {
        Text("Click me")
    }
}

// 注意，这里 DisplayText1在 count 更改的时候会重新组合两次????
//	1.	初次组合：clickHandlerToPrintRememberUpdatedState 被初始化，记录了当时的 count 值。
// 	2.	后续重组：rememberUpdatedState 确保 clickHandlerToPrintRememberUpdatedState 在每次重组时更新为最新的 count 值。因此，每次 count 更新时，clickHandlerToPrintRememberUpdatedState 也会更新，并触发重组。


@Composable
fun DisplayText2(count: Int, checked: Boolean) {
    // rememberUpdatedState 会在每次重组时更新 clickHandlerToPrintRememberUpdatedState，确保它始终使用最新的状态。
    val clickHandlerToPrintRememberUpdatedState1 by rememberUpdatedState(newValue = {
        // 去掉下面这行 Log ，就会无限重组，为什么？
        // Log.d("Tips25Screen", "Button clicked $count times")
        Unit
    })
    Log.d("Tips25Screen", "DisplayText2 Button hashCode: ${clickHandlerToPrintRememberUpdatedState1.hashCode()}")

    Switch(checked = checked, onCheckedChange = null)

    Text("$count")

    Button(
        interactionSource = NoRippleInteractionSource(), onClick = clickHandlerToPrintRememberUpdatedState1
    ) {
        Text("Click me")
    }

}

@Composable
fun DisplayText3(count: Int, checked: Boolean) {
    // rememberUpdatedState 会在每次重组时更新 clickHandlerToPrintRememberUpdatedState，确保它始终使用最新的状态。
    val rememberCount by rememberUpdatedState(count)

    Switch(checked = checked, onCheckedChange = null)

    Text("$count")

    Button(interactionSource = NoRippleInteractionSource(), onClick = {
        Log.d("Tips25Screen", "DisplayText1 Button clicked count: $count times")
        Log.d("Tips25Screen", "DisplayText1 Button clicked rememberCount: $rememberCount times")
    }) {
        Text("Click me")
    }
}
