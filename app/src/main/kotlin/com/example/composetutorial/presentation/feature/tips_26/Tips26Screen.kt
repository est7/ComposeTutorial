package com.example.composetutorial.presentation.feature.tips_26

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.utils.EmptyContent.contentType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tips26Screen() {
    val list1 by remember {
        mutableStateOf(tips26List1)
    }
    val list2 by remember {
        mutableStateOf(tips26List2)
    }
    val list3 by remember {
        mutableStateOf(tips26List3)
    }

    val list4 by remember {
        mutableStateOf(tips26List4)
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }
    var switch by remember {
        mutableStateOf(false)
    }

    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val coroutineScope = rememberCoroutineScope()

        PullToRefreshBox(isRefreshing = isRefreshing,
            onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                delay(2000)
                switch = !switch
                isRefreshing = false
            }
        }) {
            LazyColumn(
                state = lazyListState,
            ) {
                item(contentType = "headerType") {
                    if (isRefreshing) {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.fillMaxSize().padding(top = 100.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Text(
                            text = "Loaded",
                            modifier = Modifier.fillMaxSize().padding(top = 100.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                itemsIndexed(
                    items = if (!switch) list1 else list4, key = { index, item -> item.id },
                    contentType = { index: Int, item: Tips26Item -> "contentType" },
                ) { _, item ->
                    Tips26Item(data = item)
                }

            item(contentType = "footerType") {
                    if (isRefreshing) {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.fillMaxSize(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Text(
                            text = "Loaded",
                            modifier = Modifier.fillMaxSize(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
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


val tips26List1 = persistentListOf(
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

val tips26List2 = persistentListOf(
    Tips26Item(id = 11, title = "11"),
    Tips26Item(id = 12, title = "12"),
    Tips26Item(id = 13, title = "13"),
    Tips26Item(id = 14, title = "14"),
    Tips26Item(id = 15, title = "15"),
    Tips26Item(id = 16, title = "16"),
    Tips26Item(id = 17, title = "17"),
    Tips26Item(id = 18, title = "18"),
    Tips26Item(id = 19, title = "19"),
    Tips26Item(id = 20, title = "20")
)

val tips26List3 = persistentListOf(
    Tips26Item(id = 1, title = "11"),
    Tips26Item(id = 2, title = "12"),
    Tips26Item(id = 3, title = "13"),
    Tips26Item(id = 4, title = "14"),
    Tips26Item(id = 5, title = "15"),
    Tips26Item(id = 6, title = "16"),
    Tips26Item(id = 7, title = "17"),
    Tips26Item(id = 8, title = "18"),
    Tips26Item(id = 9, title = "19"),
    Tips26Item(id = 10, title = "20")
)
val tips26List4 = persistentListOf(
    Tips26Item(id = 1, title = "1"),
    Tips26Item(id = 2, title = "2"),
    Tips26Item(id = 3, title = "3"),
    Tips26Item(id = 4, title = "4"),
    Tips26Item(id = 5, title = "five"),
    Tips26Item(id = 6, title = "6"),
    Tips26Item(id = 7, title = "7"),
    Tips26Item(id = 8, title = "8"),
    Tips26Item(id = 9, title = "9"),
    Tips26Item(id = 10, title = "0")
)