package com.example.composetutorial.presentation.feature.tips_03

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Tips03Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
        ) {
            EmailScreen()
            EmailScreenWithSaveable()
            EmailScreenWithViewModel()
        }
    }
}


//在 Jetpack Compose 中，remember 函数用于在重新组合时保留变量的值。然而，当发生配置更改（如屏幕方向的改变）时，
// Compose 会销毁并重新创建所有的 Composable 函数，因此 remember 保存的状态也会被重置。
@Composable
fun EmailScreen() {
    var emailText by remember { mutableStateOf("") }
    TextField(value = emailText, onValueChange = { emailText = it })
}
@Composable
fun EmailScreenWithSaveable() {
    var emailText by rememberSaveable { mutableStateOf("") }
    TextField(value = emailText, onValueChange = { emailText = it })
}


@Composable
fun EmailScreenWithViewModel(viewModel: LoginViewModel = koinViewModel()) {
    TextField(
        value = viewModel.emailText.value, onValueChange = viewModel::onEmailTextChanged
    )
}