package com.example.composetutorial.presentation.feature.tips_14

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.presentation.feature.tips_08.BadLoginScreen

@Composable
fun Tips14Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            Tips14BadLoginScreen(state = Tips14BadLoginState())
            Tips14GoodLoginScreen(state = Tips14GoodLoginState())
        }
    }
}

@Composable
fun Tips14GoodLoginScreen(state: Tips14GoodLoginState) {
    if (state.isLoggingIn) {
        CircularProgressIndicator()
    } else if (state.isLoginFailed) {
        Text("Login failed")
    } else {
        Column {
            TextInputField(value = state.emailText, onValueChange = {}, label = "Email")
            TextInputField(value = state.emailText, onValueChange = {}, label = "Password")
            Button(onClick = {}) {
                Text("Login")
            }
        }
    }

}

@Composable
fun Tips14BadLoginScreen(state: Tips14BadLoginState) {
    if (state.isProgressBarVisible) {
        CircularProgressIndicator()
    } else if (state.isLoginFailed) {
        Text("Login failed")
    } else {
        Column {
            TextInputField(value = state.email, onValueChange = {}, label = "Email")
            TextInputField(value = state.email, onValueChange = {}, label = "Password")
            Button(onClick = {}) {
                Text("Login")
            }
        }
    }
}

@Composable
fun TextInputField(value: String, onValueChange: () -> Unit, label: String) {


}


data class Tips14BadLoginState(
    val email: String = "", val isProgressBarVisible: Boolean = false, val isLoginFailed: Boolean = false
)

data class Tips14GoodLoginState(
    val emailText: String = "", val isLoggingIn: Boolean = false, val isLoginFailed: Boolean = false
)