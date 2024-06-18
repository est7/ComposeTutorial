package com.example.composetutorial.presentation.feature.tips_26

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tips26Screen() {
    var list by remember {
        mutableStateOf(tips26List)
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val coroutineScope = rememberCoroutineScope()

        PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                delay(2000)
                list = list.add(Tips26Item(id = list.size + 1, title = "${list.size + 1}"))
                isRefreshing = false
            }
        }) {
            LazyColumn {
                itemsIndexed(items = list, key = { index, item -> index }) { _, item ->
                    Tips26Item(data = item)
                }
            }
        }
    }
}

@Composable
fun Tips26Item(data: Tips26Item) {
    Text(
        text = data.title, modifier = Modifier
            .fillMaxSize()
            .size(100.dp)
    )
}


data class Tips26Item(
    val id: Int,
    val title: String,
)


val tips26List = persistentListOf(
    Tips26Item(id = 1, title = "1"),
    Tips26Item(id = 2, title = "2"),
    Tips26Item(id = 3, title = "3"),
    Tips26Item(id = 4, title = "4"),
    Tips26Item(id = 5, title = "5"),
    Tips26Item(id = 6, title = "6"),
    Tips26Item(id = 7, title = "7"),
    Tips26Item(id = 8, title = "8"),
    Tips26Item(id = 9, title = "9"),
    Tips26Item(id = 10, title = "10")
)