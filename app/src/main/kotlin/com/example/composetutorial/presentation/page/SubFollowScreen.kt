package com.example.composetutorial.presentation.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.presentation.page.common.EmptyPage
import com.example.composetutorial.presentation.page.common.FailedPage
import com.example.composetutorial.presentation.page.common.LoadingPage
import com.example.composetutorial.presentation.viewmodel.FollowSubPageScreenUiState
import com.example.composetutorial.presentation.viewmodel.ListLoadState
import com.example.composetutorial.presentation.viewmodel.SubFollowViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubFollowScreen(
    viewModel: SubFollowViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.subFollowScreenUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.refreshSubFollowPageList(type = "follow")
    }


    val onClickBuilder = remember {
        FollowItemClick {
            onClickFollow = {
                Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show()
                // viewModel.loadMoreFollowSubPageList(type = "follow")
            }
            onClickName = {
                // viewModel.refreshSubFollowPageList(type = "follow")
            }
            onRefresh = {
                viewModel.refreshSubFollowPageList(type = "follow")
            }

            onLoadMore = {
                viewModel.loadMoreFollowSubPageList(type = "follow")
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        FollowScreen(uiState, onClickBuilder = onClickBuilder)
    }
}

@Composable
fun FollowScreen(uiState: FollowSubPageScreenUiState, onClickBuilder: FollowItemClick) {
    when (uiState) {
        FollowSubPageScreenUiState.Initial -> LoadingPage()
        is FollowSubPageScreenUiState.Loaded -> SubFollowList(data = uiState.data,
            state = uiState.listState,
            onClickBuilder = onClickBuilder,
            onRefresh = {
                Log.d("SubFollowScreen", "onRefresh")
                onClickBuilder.onRefresh?.invoke()
            },
            onLoadMore = {
                Log.d("SubFollowScreen", "onLoadMore")
                onClickBuilder.onLoadMore?.invoke()
            })

        is FollowSubPageScreenUiState.Empty -> EmptyPage()
        is FollowSubPageScreenUiState.LoadFailed -> FailedPage(message = uiState.message, onClickRetry = { })
        FollowSubPageScreenUiState.EmptyLoading -> LoadingPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubFollowList(
    modifier: Modifier = Modifier,
    data: ImmutableList<ComposeTipsItemDTO>,
    state: ListLoadState,
    onClickBuilder: FollowItemClick,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier, state = pullToRefreshState, isRefreshing = state.isGettingRefreshing, onRefresh = onRefresh
    ) {
        val lazyListState = rememberLazyListState()
        Log.d("SubFollowList", "state=${state}")
        Log.d("SubFollowList", "data=${data}")

        LazyColumn(
            modifier = Modifier.fillMaxSize(), state = lazyListState
        ) {
            itemsIndexed(items = data, key = { _, item -> item.id }) { index, item ->
                FollowListItem(data = item, onClick = onClickBuilder.onClickFollow ?: {})
            }

            listFooter(
                isLoadingMore = state.isGettingLoadingMore,
                noMoreData = state.isNoMoreData,
                loadFailed = state.isLoadFailed,
                errorMessage = (state as? ListLoadState.LoadingMoreLoadFailed)?.message
            )
        }

        Log.d("SubFollowList", "data.size=${data.size}")
        Log.d("SubFollowList", "data=${data.hashCode()}")
        LoadMoreListener(
            lazyListState = lazyListState,
            listSize = data.size,
            onLoadMore = onLoadMore,
            noMoreData = state.isNoMoreData,
            isLoadingMore = state.isGettingLoadingMore
        )
    }
}

@Composable
fun LoadMoreListener(
    lazyListState: LazyListState, listSize: Int, onLoadMore: () -> Unit, noMoreData: Boolean, isLoadingMore: Boolean
) {
    LaunchedEffect(listSize) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }.filter { it.isNotEmpty() }
            .map { it.lastOrNull()?.index }.distinctUntilChanged().collect { index ->
                Log.d("LoadMoreListener", "index=$index")
                Log.d("LoadMoreListener", "listSize=$listSize")
                if (index == listSize - 1 && !noMoreData && !isLoadingMore) {
                    onLoadMore()
                }
            }
    }
}

fun LazyListScope.listFooter(
    isLoadingMore: Boolean,
    noMoreData: Boolean,
    loadFailed: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
) {

    if (isLoadingMore) {
        item {
            CircularProgressIndicator(
                modifier = modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }
    }

    if (noMoreData) {
        item {
            Text(
                text = "No more data", modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp), textAlign = TextAlign.Center
            )
        }
    }

    if (loadFailed) {
        item {
            Text(
                text = errorMessage ?: "Load failed",
                color = Color.Red,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FollowListItem(data: ComposeTipsItemDTO, onClick: (() -> Unit)?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = { onClick?.invoke() })
    ) {
        Text(text = "Item ${data.id}")
    }
}

@Preview
@Composable
fun PreviewSubFollowList() {
    // SubFollowList(state = ListState(data = listOf(ComposeTipsItemDTO(id = 1))))
}

class FollowItemClick(build: OnFollowItemClickBuilder.() -> Unit) {
    private val builder = OnFollowItemClickBuilder().apply(build)

    val onClickFollow: (() -> Unit)? get() = builder.onClickFollow
    val onClickName: (() -> Unit)? get() = builder.onClickName
    val onRefresh: (() -> Unit)? get() = builder.onRefresh
    val onLoadMore: (() -> Unit)? get() = builder.onLoadMore

    class OnFollowItemClickBuilder {
        var onClickFollow: (() -> Unit)? = null
        var onClickName: (() -> Unit)? = null
        var onRefresh: (() -> Unit)? = null
        var onLoadMore: (() -> Unit)? = null
    }
}