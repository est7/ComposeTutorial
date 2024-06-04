package com.example.composetutorial.uiwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    //
    Icon(imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier.padding(8.dp).clickable { onClick() })
}