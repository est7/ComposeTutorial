package com.example.composetutorial.presentation.feature.tips_27

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tips27Screen(viewModel: MainViewModel = koinViewModel()) {
    var refreshCount by remember { mutableIntStateOf(0) }
    var data = remember { ComposeTipsItemDTO(0, "path/to/data/1", "desc") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
        ) {
            Text(text = "refreshCount = $refreshCount")

            Button(onClick = {
                refreshCount += 1
                // data = ComposeTipsItemDTO("$refreshCount", "1", "1")
            }) {
                Text(text = "refresh")
            }
            Tips27ComplexItem(data, onClick = null)

            Tips27ComplexItem(data, onClick = {
                // 因为有 viewmoel ，所以没跳过
                onClickItem = {
                    viewModel.getComposeTipsList()
                }
            })

            Tips27ComplexItem(data, onClick = {
                onClickItem = {
                    refreshCount += 1
                }
            })
            Tips27ComplexItem(data, onClick = {
                onClickItem = {
                    refreshCount += 1
                }
                onClickFollow = {
                    viewModel.getComposeTipsList()
                }
            })


            Tips27ComplexItemRemember(data, onClick = {
                onClickItem = {
                    refreshCount += 1
                }
                onClickFollow = {
                    viewModel.getComposeTipsList()
                }
            })

        }

    }
}

@Composable
fun Tips27ComplexItem(data: ComposeTipsItemDTO, onClick: (Tips27FollowItemClick.() -> Unit)?) {
    val clickListener = remember {
        if (onClick != null) Tips27FollowItemClick().apply(onClick) else null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable(onClick = { clickListener?.onClickItem?.invoke() })
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            // 注意下面三个 Text 的重组情况
            // 导致重组的原因是
            // .clickable(onClick = { clickListener?.onClickName?.invoke() })

            // 1
            Text(
                text = "Item Name: ${data.id}",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = { clickListener?.onClickName?.invoke() })
            )

            // 2
            Text(
                text = "Item Name: ${data.id}",
                modifier = Modifier
                    .padding(16.dp)
            )


            // 3
            Text(
                text = "固定文字",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = { clickListener?.onClickName?.invoke() })
            )

            // 4
            Button(
                onClick = { clickListener?.onClickName?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "onClickName")
            }
            // 3 和 4 虽然都是onClickName ,但是每次重组时，Modifier 都会重新创建，导致 clickable 修饰符被重新应用
            // 也就是 3 会重组，但 4 不会重组

            // 5
            Button(
                onClick = { clickListener?.onClickFollow?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "onClickFollow")
            }

            // 6
            Button(
                onClick = { clickListener?.onClickRemove?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "onClickRemove")
            }

        }
    }
}


@Composable
fun Tips27ComplexItemRemember(data: ComposeTipsItemDTO, onClick: (Tips27FollowItemClick.() -> Unit)?) {
    val clickListener = remember {
        if (onClick != null) Tips27FollowItemClick().apply(onClick) else null
    }
    val clickNameFun by remember {
        derivedStateOf {
            clickListener?.onClickName
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable(onClick = { clickListener?.onClickItem?.invoke() })
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            // 注意下面三个 Text 的重组情况
            // 导致重组的原因是
            // .clickable(onClick = { clickListener?.onClickName?.invoke() })

            // 1
            Text(
                text = "Item Name: ${data.id}",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = { clickListener?.onClickName?.invoke() })
            )

            // 2
            Text(
                text = "Item Name: ${data.id}",
                modifier = Modifier
                    .padding(16.dp)
            )


            // 3
            Text(
                text = "固定文字",
                modifier = clickNameFun?.let {
                    Modifier
                        .padding(16.dp)
                        .clickable(onClick = it)
                } ?: run {
                    Modifier
                        .padding(16.dp)
                }
            )

            // 4
            Button(
                onClick = { clickListener?.onClickName?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "onClickName")
            }
            // 3 和 4 虽然都是onClickName ,但是每次重组时，Modifier 都会重新创建，导致 clickable 修饰符被重新应用
            // 也就是 3 会重组，但 4 不会重组

            // 5
            Button(
                onClick = { clickListener?.onClickFollow?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "onClickFollow")
            }

            // 6
            Button(
                onClick = { clickListener?.onClickRemove?.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "onClickRemove")
            }

        }
    }
}


class Tips27FollowItemClick() {
    var onClickItem: (() -> Unit)? = null
    var onClickFollow: (() -> Unit)? = null
    var onClickName: (() -> Unit)? = null
    var onClickRemove: (() -> Unit)? = null
}
