package com.example.composetutorial.presentation.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import org.koin.core.component.getScopeName

@Composable
fun SubFollowScreen(
    viewModel: SubFollowViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.subFollowScreenUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.refreshSubFollowPageList(type = "follow")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        FollowScreen(uiState, action = {
            when (it) {
                SubFollowPageAction.Refresh -> viewModel.refreshSubFollowPageList(type = "follow")
                SubFollowPageAction.LoadMore -> viewModel.loadMoreFollowSubPageList(type = "follow")
            }
        }) {
            onClickFollow = {
                // Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show()
                // viewModel.loadMoreFollowSubPageList(type = "follow")
            }
            onClickName = {
                // viewModel.refreshSubFollowPageList(type = "follow")
            }
            onClickItem = {
                // Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show()
            }
            onClickRemove = {
                // Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun FollowScreen(
    uiState: FollowSubPageScreenUiState,
    action: ((SubFollowPageAction) -> Unit)?,
    onClickItemListener: (FollowItemClick.() -> Unit)?,
) {
    Log.d("SubFollowScreen", "FollowScreen: uiState = $uiState")
    val context = LocalContext.current
    when (uiState) {
        FollowSubPageScreenUiState.Initial -> LoadingPage()
        is FollowSubPageScreenUiState.Loaded -> {
            when (uiState.listState) {
                is ListLoadState.RefreshingLoadFailed -> {
                    Toast.makeText(context, uiState.listState.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
            SubFollowList(data = uiState.data,
                state = uiState.listState,
                onClickItemListener = onClickItemListener,
                onRefresh = {
                    action?.let { it(SubFollowPageAction.Refresh) }
                },
                onLoadMore = {
                    action?.let { it(SubFollowPageAction.LoadMore) }
                })
        }

        is FollowSubPageScreenUiState.Empty -> EmptyPage()
        is FollowSubPageScreenUiState.LoadFailed -> FailedPage(message = uiState.message, onClickRetry = {
            action?.let { it(SubFollowPageAction.Refresh) }
        })

        FollowSubPageScreenUiState.EmptyLoading -> LoadingPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubFollowList(
    modifier: Modifier = Modifier,
    data: ImmutableList<ComposeTipsItemDTO>,
    state: ListLoadState,
    onClickItemListener: (FollowItemClick.() -> Unit)?,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier, state = pullToRefreshState, isRefreshing = state.isGettingRefreshing, onRefresh = onRefresh
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(), state = lazyListState
        ) {
            itemsIndexed(items = data, key = { _, item -> item.id }) { _, item ->
                FollowListItem(data = item, onClick = onClickItemListener)
            }

            /*
                        listFooter(
                            isGettingLoadingMore = state.isGettingLoadingMore,
                            noMoreData = state.isNoMoreData,
                            loadFailed = state.isLoadingMoreLoadFailed,
                            errorMessage = (state as? ListLoadState.LoadingMoreLoadFailed)?.message
                        )
            */
        }
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
    // 这里如果不使用 rememberUpdatedState 会导致 listSize 不变化，导致 onLoadMore  不会被调用在后面 listSize 发生变化
    val size by rememberUpdatedState(listSize)

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }.filter { it.isNotEmpty() }
            .map { it.lastOrNull()?.index }.distinctUntilChanged().collect { index ->
                if (index == size - 1 && !noMoreData && !isLoadingMore) {
                    onLoadMore()
                }
            }
    }
}

fun LazyListScope.listFooter(
    isGettingLoadingMore: Boolean,
    noMoreData: Boolean,
    loadFailed: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
) {

    when {
        isGettingLoadingMore -> {
            item {
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .size(24.dp)
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "Loading", modifier = modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        noMoreData -> {
            item {
                Text(
                    text = "No more data",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        loadFailed -> {
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
}

@Composable
fun FollowListItem(data: ComposeTipsItemDTO, onClick: (FollowItemClick.() -> Unit)?) {
    // val clickListener = remember {
    //     if (onClick != null) FollowItemClick().apply(onClick) else null
    // }

    val clickListener by rememberUpdatedState(newValue = onClick?.let { FollowItemClick().apply(it) })
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = { clickListener?.onClickItem?.invoke() })
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            Text(
                text = "Item Name: ${data.path}",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = { clickListener?.onClickName?.invoke() })
            )

            Button(
                onClick = { clickListener?.onClickFollow?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Follow")
            }


            Button(
                onClick = { clickListener?.onClickRemove?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "set block")
            }


        }
    }
}

@Preview
@Composable
fun PreviewSubFollowListItem() {
    FollowListItem(data = ComposeTipsItemDTO(id = "lacinia", path = "fugit", desc = "justo"), onClick = {})
}


class FollowItemClick() {
    var onClickItem: (() -> Unit)? = null
    var onClickFollow: (() -> Unit)? = null
    var onClickName: (() -> Unit)? = null
    var onClickRemove: (() -> Unit)? = null
}


sealed class SubFollowPageAction {
    data object Refresh : SubFollowPageAction()
    data object LoadMore : SubFollowPageAction()
}