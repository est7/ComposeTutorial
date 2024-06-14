package com.example.composetutorial.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        //search bar
        Text(text = "FollowScreen")
        //FollowList
        FollowList()


    }
}

@Composable
fun FollowScreen(

)