package com.example.composetutorial.presentation.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.R
import com.example.composetutorial.navagation.Destination
import com.example.composetutorial.uiwidget.ClickableIcon
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigate: (Destination) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0) { 3 }
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(stringResource(R.string.app_name)) }, actions = {
            ClickableIcon(
                imageVector = Icons.Default.AccountCircle,
            ) {
                onNavigate(Destination.MyProfile)
            }
        })
    }, bottomBar = {
        Column {
            NavigationBar {
                NavigationBarItem(icon = {
                    Icon(
                        imageVector = Icons.Default.Home, contentDescription = "home"
                    )
                }, label = { Text("Home") }, selected = (pagerState.currentPage == 0), onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage)
                    }
                })
                NavigationBarItem(icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite, contentDescription = "follow"
                    )
                }, label = { Text("Follow") }, selected = (pagerState.currentPage == 1), onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage)
                    }
                })
                NavigationBarItem(icon = {
                    Icon(
                        imageVector = Icons.Default.Settings, contentDescription = "settings"
                    )
                }, label = { Text("Settings") }, selected = (pagerState.currentPage == 2), onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage)
                    }
                })

            }
        }
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                beyondViewportPageCount = 3,
                state = pagerState, modifier = Modifier.fillMaxSize()
            ) { index ->
                when (index) {
                    0 -> HomeScreen()
                    1 -> SubFollowScreen()
                    2 -> SettingScreen()
                }
            }
        }
    }
}
