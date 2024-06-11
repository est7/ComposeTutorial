package com.example.composetutorial.presentation.feature.tips_20

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Tips20Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Tips20BadLoginScreen("Error message")
        HorizontalDivider()
        Tips20GoodLoginScreen("Error message")
    }
}

// 在这个示例中，如果 loginError 不为空，return 会导致 Text 组件被跳过，可能会引起未定义的行为。
@Composable
fun Tips20BadLoginScreen(loginError: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth()
        )
        BasicTextField(
            value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth()
        )
        if (loginError != null) {
            return
        }
        Text(
            text = "No errors", modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun Tips20GoodLoginScreen(loginError: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth()
        )
        BasicTextField(
            value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth()
        )
        loginError?.let { error ->
            Text(
                text = error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp)
            )
        } ?: run {
            Text(
                text = "No errors", modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}