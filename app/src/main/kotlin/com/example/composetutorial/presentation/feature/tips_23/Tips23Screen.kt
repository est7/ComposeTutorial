package com.example.composetutorial.presentation.feature.tips_23

import android.util.Log
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.presentation.feature.tips_01.NoRippleInteractionSource

@Composable
fun Tips23Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
        ) {
            Tips23BadDerivedStateExample()
            HorizontalDivider()
            Tips23GoodDerivedStateExample()
        }
    }
}

@Composable
fun Tips23BadDerivedStateExample() {
    var count by remember { mutableIntStateOf(0) }
    var count2 by remember { mutableIntStateOf(0) }
    val divisibleBy3 = count % 3 == 0 //这里不是比较重的计算，但是可能如果有的话，要避免

    Column {
        Button(interactionSource = NoRippleInteractionSource(), onClick = { count++ }) {
            Text("Increment")
        }
        Text("Count is: $count")
        if (divisibleBy3) {
            Text("可以被三整除")
        } else {
            Text("不能被三整除")
        }
        Button(interactionSource = NoRippleInteractionSource(), onClick = { count2++ }) {
            Text("Increment")
        }
        Text("Count is: $count2")

    }
}

@Composable
fun Tips23GoodDerivedStateExample() {
    var count by remember { mutableIntStateOf(0) }
    var count2 by remember { mutableIntStateOf(0) }
    // 能否被 3 整除
    val divisibleBy3 by remember {
        //在这个例子中，每次点击按钮，count 的值会增加，
        // 但只有在 count 发生变化时，derivedStateOf 才会重新计算 divisibleBy3。
        // 可以通过查看日志输出，验证 derivedStateOf 确实避免了不必要的重新计算。
        Log.d("DerivedState", "Calculating divisibleBy3")
        derivedStateOf { count % 3 == 0 }
    }

    Column {
        Button(interactionSource = NoRippleInteractionSource(), onClick = { count++ }) {
            Text("Increment")
        }
        Text("Count is: $count")
        if (divisibleBy3) {
            Text("可以被三整除")
        } else {
            Text("不能被三整除")
        }
        Button(interactionSource = NoRippleInteractionSource(), onClick = { count2++ }) {
            Text("Increment")
        }
        Text("Count is: $count2")
    }
}