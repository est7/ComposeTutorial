package com.example.composetutorial.presentation.feature.tips_02

import android.util.Log
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.presentation.feature.tips_01.BadBookingList
import com.example.composetutorial.presentation.feature.tips_01.GoodBookingList
import com.example.composetutorial.presentation.feature.tips_01.NoRippleInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun Tips02Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column {
            NamesMutableList()
            NamesImmutableList()

        }
    }
}

@Composable
fun NamesMutableList() {
    val names by remember {
        mutableStateOf(mutableListOf<String>())
    }
    // 这里因为names永远都是同一个对象，所以不会触发Compose的重绘
    LazyColumn {
        item {
            Button(onClick = {
                names.add("Hans")
            }) {
                Text("Add name")
            }
        }
        items(names) { name ->
            Text(name)
        }
    }
}


@Composable
fun NamesImmutableList() {
    var names by remember {
        mutableStateOf(listOf<String>())
    }

    // 这里因为names是一个新的对象，所以会触发Compose的重绘
    LazyColumn {
        item {
            Button(onClick = {
                names = names + "Hans"
            }) {
                Text("Add name")
            }
        }
        items(names) { name ->
            Text(name)
        }
    }
}