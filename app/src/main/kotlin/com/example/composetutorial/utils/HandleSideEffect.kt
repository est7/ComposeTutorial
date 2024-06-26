package com.example.composetutorial.utils

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import com.example.composetutorial.presentation.viewmodel.MyProfileSideEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
@Composable
fun HandleMyProfileSideEffect(
    viewModel: LoginViewModel, handler: CoroutineScope.(sideEffect: MyProfileSideEffect) -> Unit = {}
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            handler.invoke(this, effect)
            when (effect) {
                is MyProfileSideEffect.NavigateTo -> {}
                is MyProfileSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}