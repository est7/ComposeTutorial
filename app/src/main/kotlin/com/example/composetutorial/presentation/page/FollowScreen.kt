package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Greeting(viewModel = koinViewModel())
    }
}

@Composable
fun Greeting(
    viewModel: LoginViewModel, modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Andy") }
    val scope = rememberCoroutineScope()

    var clickFun = remember {
        {
            name = if (name == "Andy") {
                scope.launch {
                    viewModel.logout()
                }
                "Bob"
            } else {
                "Andy"
            }
        }
    }

    Column {
        Name("李华")
        Name(name = name)

        Log.d("lilili", scope.toString())
        Button(onClick = {
            name = if (name == "Andy") {
                scope.launch {
                    // viewModel.logout()
                }
                "Bob"
            } else {
                "Andy"
            }
        }) {
            Text(text = "Change name")
        }

        Button(onClick = clickFun) {
            Text(text = "Change name 1")
        }

    }
}


@Composable
fun Name(name: String) {
    Text(text = "Hello $name!")
}


@Preview(name = "FollowList")
@Composable
private fun PreviewFollowList() {
    FollowScreen()
}
