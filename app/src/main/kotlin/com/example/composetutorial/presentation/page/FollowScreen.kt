package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Greeting(hero, viewModel = koinViewModel())
    }
}

val hero = Hero("Andy", 18)

@Composable
fun Greeting(
    hero: Hero, viewModel: LoginViewModel, modifier: Modifier = Modifier
) {
    var name1 by remember { mutableStateOf("Andy") }
    val scope = rememberCoroutineScope()
    // var hero by remember {
    //     mutableStateOf(Hero("Andy", 18))
    // }

    val clickFun = remember {
        {
            name1 = if (name1 == "Andy") {
                viewModel.logout()
                "Bob"
            } else {
                "Andy"
            }
        }
    }

    Column {
        HeroInfo(hero)
        Text(text = "${name1} :Hello ${hero.name}!")
        Text(text = "Hello ${hero.age}!")
        Button(onClick = {

            hero.age = 20

            // hero = hero.copy(age = 20)

            // viewModel.logout()

            name1 = if (name1 == "Andy") {
                // scope.launch {
                // viewModel.logout()
                // }
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

@Preview(name = "FollowList")
@Composable
private fun PreviewFollowList() {
    FollowScreen()
}




@Composable
fun HeroInfo(hero: Hero) {
    Text(text = "Hello ${hero.name}!")
    Text(text = "Hello ${hero.age.toString()}!")
}

@Stable
data class Hero(val name: String, var age: Int)

// @Immutable
// data class Hero(val name: String, var age: Int)

