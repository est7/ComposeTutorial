package com.example.composetutorial.presentation.page

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.presentation.page.common.EmptyPage
import com.example.composetutorial.presentation.page.common.FailedPage
import com.example.composetutorial.presentation.page.common.LoadingPage
import com.example.composetutorial.presentation.viewmodel.FollowSubPageScreenUiState
import com.example.composetutorial.presentation.viewmodel.ListLoadState
import com.example.composetutorial.presentation.viewmodel.SubFollowViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun SubFollowScreen(
    viewModel: SubFollowViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.subFollowScreenUiState.collectAsStateWithLifecycle()
    val type = "follow"

    LaunchedEffect(Unit) {
        viewModel.onSubFollowAction(SubFollowPageAction.Refresh(type))
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
/*
        val action: (SubFollowPageAction) -> Unit = {
            when (it) {
                is SubFollowPageAction.onClickItem -> {
                    Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show()
                }

                is SubFollowPageAction.onClickName -> {
                    Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
            viewModel.onSubFollowAction(it)
        }
*/
        FollowScreen(uiState, action = viewModel::onSubFollowAction)
    }
}


@Composable
fun FollowScreen(
    uiState: FollowSubPageScreenUiState,
    action: ((SubFollowPageAction) -> Unit),
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
            SubFollowList(
                data = uiState.data, state = uiState.listState, action = action
            )
        }

        is FollowSubPageScreenUiState.Empty -> EmptyPage()
        is FollowSubPageScreenUiState.LoadFailed -> FailedPage(message = uiState.message,
            onClickRetry = {
                action(SubFollowPageAction.Refresh("follow"))
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
    action: ((SubFollowPageAction) -> Unit)
) {
    Log.d("SubFollowScreen", "SubFollowList: data = ${data.hashCode()},state = ${state.hashCode()}")
    val pullToRefreshState = rememberPullToRefreshState()

    val isRefreshing by rememberUpdatedState(state.isRefreshing)
    val isLoadingMore by rememberUpdatedState(state.isLoadingMore)
    val isNoMoreData by rememberUpdatedState(state.isNoMoreData)
    val isLoadMoreFailed by rememberUpdatedState(state.isLoadMoreFailed)
    val errorMessage by rememberUpdatedState((state as? ListLoadState.LoadingMoreLoadFailed)?.message)

    CustomPullToRefreshBox(modifier = modifier,
        state = pullToRefreshState,
        isRefreshing = state.isRefreshing,
        paddingValues = PaddingValues(top = 0.dp),
        onRefresh = {
            action(SubFollowPageAction.Refresh("follow"))
        }) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(), state = lazyListState
        ) {

            itemsIndexed(items = data, key = { _, item -> item.id }, contentType = { index, item ->
                "type_content"
            }) { index, item ->
                FollowListItem(index = index, data = item, action = action)
            }

            listFooter(
                isGettingLoadingMore = isLoadingMore,
                noMoreData = isNoMoreData,
                loadFailed = isLoadMoreFailed,
                onClick = { action(SubFollowPageAction.LoadMore("follow")) },
                errorMessage = errorMessage
            )
        }

        LoadMoreListener(
            lazyListState = lazyListState,
            listSize = data.size,
            onLoadMore = { action(SubFollowPageAction.LoadMore("follow")) },
            noMoreData = state.isNoMoreData,
            isLoadingMore = state.isLoadingMore
        )
    }
}

@Composable
fun FollowListItem(
    index: Int = 0,
    data: ComposeTipsItemDTO,
    action: (SubFollowPageAction) -> Unit = { sub -> Unit }
) {
    Log.d(
        "SubFollowScreen",
        "FollowListItem: index = $index, data = ${data.hashCode()},action = ${action.hashCode()}"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                action(
                    SubFollowPageAction.onClickItem(
                        position = index, item = data
                    )
                )
            })
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            // 这里的clickable(onClick = { action(SubFollowPageAction.onClickName(index, data)) })也会导致 text 重组
            Text(

                text = "Item Name: ${data.path}",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = { action(SubFollowPageAction.onClickName(index, data)) })
            )

            Button(
                onClick = { action(SubFollowPageAction.onClickFollow(index, data)) },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Follow")
            }


            Button(
                onClick = { action(SubFollowPageAction.onClickRemove(index, data)) },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Set block")
            }


        }
    }
}

@Composable
fun LoadMoreListener(
    lazyListState: LazyListState,
    listSize: Int,
    onLoadMore: () -> Unit,
    noMoreData: Boolean,
    isLoadingMore: Boolean
) {
    // 这里如果不使用 rememberUpdatedState 会导致 listSize 不变化，导致 onLoadMore  不会被调用在后面 listSize 发生变化
    val size by rememberUpdatedState(listSize)
    val rememberNoMoreData by rememberUpdatedState(noMoreData)
    val rememberIsLoadingMore by rememberUpdatedState(isLoadingMore)

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }.filter { it.isNotEmpty() }
            .map { it.lastOrNull()?.index }.distinctUntilChanged().collect { index ->
                Log.d(
                    "SubFollowScreen-LoadMoreListener",
                    "listSize = $listSize ,noMoreData = $noMoreData,isLoadingMore = $isLoadingMore"
                )
                Log.d(
                    "SubFollowScreen-LoadMoreListener",
                    "LoadMoreListener: index = $index, size = $size, noMoreData = $rememberNoMoreData, isLoadingMore = $rememberIsLoadingMore"
                )

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
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    when {
        isGettingLoadingMore -> {
            item(contentType = "type_footer") {
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
                        text = "Loading",
                        modifier = modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        noMoreData -> {
            item(contentType = "type_footer") {
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
            item(contentType = "type_footer") {
                Text(
                    text = errorMessage ?: "Load failed",
                    color = Color.Red,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable(onClick = onClick),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSubFollowListItem() {
    val action = { action: SubFollowPageAction ->
        Unit
    }
    FollowListItem(
        index = 0,
        data = ComposeTipsItemDTO(id = 1001, path = "fugit", desc = "justo"),
        action = action
    )
}


sealed class SubFollowPageAction {
    data class Refresh(val type: String) : SubFollowPageAction()
    data class LoadMore(val type: String) : SubFollowPageAction()

    data class onClickItem(val position: Int, val item: ComposeTipsItemDTO) : SubFollowPageAction()
    data class onClickName(val position: Int, val item: ComposeTipsItemDTO) : SubFollowPageAction()
    data class onClickFollow(val position: Int, val item: ComposeTipsItemDTO) :
        SubFollowPageAction()

    data class onClickRemove(val position: Int, val item: ComposeTipsItemDTO) :
        SubFollowPageAction()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    paddingValues: PaddingValues,
    threshold: Dp = 120.dp,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    indicator: @Composable BoxScope.() -> Unit = {
        CustomPullToRefreshDefaults.ScalingIndicator(
            isRefreshing = isRefreshing,
            state = state,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(paddingValues)
        )
    },
    enabled: () -> Boolean = { true },
    content: @Composable BoxScope.() -> Unit,
) {
    val offsetY by animateDpAsState(
        targetValue = with(LocalDensity.current) { (state.distanceFraction * threshold) },
        label = ""
    )

    Box(
        modifier.pullToRefresh(
            state = state, isRefreshing = isRefreshing, onRefresh = onRefresh, enabled = enabled()
        )
    ) {
        Box(modifier = Modifier.offset { IntOffset(0, offsetY.roundToPx()) }) {
            content()
        }

        indicator()
    }
}

object CustomPullToRefreshDefaults {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScalingIndicator(
        isRefreshing: Boolean,
        state: PullToRefreshState,
        modifier: Modifier = Modifier,
    ) {
        val scaleFraction = {
            if (isRefreshing) 1f else LinearOutSlowInEasing.transform(state.distanceFraction)
                .coerceIn(0f, 1f)
        }
        PullToRefreshDefaults.Indicator(
            modifier = modifier.graphicsLayer {
                scaleX = scaleFraction()
                scaleY = scaleFraction()
            }, isRefreshing = isRefreshing, state = state
        )
    }
}
