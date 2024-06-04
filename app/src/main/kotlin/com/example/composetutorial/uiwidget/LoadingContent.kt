package com.example.composetutorial.uiwidget

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.testTag("loadingCircle"),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() },
    )
}