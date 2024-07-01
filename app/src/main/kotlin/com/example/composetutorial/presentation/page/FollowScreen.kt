package com.example.composetutorial.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier

) {
    val tabList = listOf(HomeTabs.Shop, HomeTabs.Profile, HomeTabs.Favourite)
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabList.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }
    val coroutineScope = rememberCoroutineScope()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            SearchBar()
            TabRow(
                selectedTabIndex = selectedTabIndex, modifier = Modifier.fillMaxWidth()
            ) {
                tabList.forEachIndexed { index, currentTab ->
                    Tab(selected = selectedTabIndex == index,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = currentTab.text) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTabIndex == index) currentTab.selectedIcon else currentTab.unselectedIcon,
                                contentDescription = "Tab Icon"
                            )
                        })
                }
            }
            HorizontalPager(
                state = pagerState, modifier = Modifier.weight(1f)
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF + (0x33 * (page + 1)) + (0x44 * page) + (0x55 * page))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Page ${page + 1}", color = Color.White, fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}


enum class HomeTabs(
    val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val text: String
) {
    Shop(
        unselectedIcon = Icons.Outlined.ShoppingCart, selectedIcon = Icons.Filled.ShoppingCart, text = "Shop"
    ),
    Favourite(
        unselectedIcon = Icons.Outlined.FavoriteBorder, selectedIcon = Icons.Filled.Favorite, text = "Favourite"
    ),
    Profile(
        unselectedIcon = Icons.Outlined.Person, selectedIcon = Icons.Filled.Person, text = "Profile"
    )
}