package com.example.composetutorial.utils

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.composetutorial.navagation.LocalNavController
import com.example.composetutorial.presentation.viewmodel.MainScreenSideEffect
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
@Composable
fun HandleMainSideEffect(
    sideEffect: Flow<MainScreenSideEffect>, handler: CoroutineScope.(sideEffect: MainScreenSideEffect) -> Unit = {}
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        sideEffect.collectLatest { effect ->
            handler.invoke(this, effect)
            when (effect) {
                is MainScreenSideEffect.NavigateTo -> {
                    navController.navigate(effect.path)
                }

                is MainScreenSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}