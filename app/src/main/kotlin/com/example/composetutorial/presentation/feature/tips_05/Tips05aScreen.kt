package com.example.composetutorial.presentation.feature.tips_05

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.presentation.feature.tips_01.NoRippleInteractionSource

/**
 *
 * @author: est8
 * @date: 2024/6/7
 */

val tips5aTag = Tips5aTag(
    id = 1, name = "tag", image = "https://example.com/profile.jpg"
)


val tips5aUserInfo = Tips5aUserInfo(
    id = 1, name = "John Doe", age = 18, tag = tips5aTag
)

@Composable
fun Tips05aScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            val count = remember { mutableIntStateOf(0) }

            Button(interactionSource = NoRippleInteractionSource(), onClick = {
                count.intValue += 1
                // tips5aTag.image = "update image"
                // tips5aUserInfo = tips5aUserInfo.copy(tag = tips5aTag.copy(image = "update image"))

            }) {
                Text(text = "Click me")
            }
            Text(text = "Count:${count.intValue}")

            ShowTips05UserInfo(tips5aUserInfo)
        }
    }
}

@Composable
fun ShowTips05UserInfo(tips5aUserInfo: Tips5aUserInfo) {
    Text(text = "id:${tips5aUserInfo.id}")
    Text(text = "name:${tips5aUserInfo.name}")
    Text(text = "age:${tips5aUserInfo.age}")
    Text(text = "-----------------")
    ShowTag(tips5aUserInfo.tag)
}

@Composable
fun ShowTag(tag: Tips5aTag) {
    Text(text = "id:${tag.id}")
    Text(text = "name:${tag.name}")
    Text(text = "image:${tag.image}")
}


// NOTE:  操作步骤，把 Stable 去掉发现会重组，但是加上就不会
// @Stable
data class Tips5aUserInfo(
    val id: Int, val name: String, val age: Int, val tag: Tips5aTag
)

// 先判断 UserInfo 有没有@stable修饰，如果有@Stable 修饰，
// 那不管属性是不是 var 的，都会跳过
// 如果没有@Stable 修饰 UserInfo
// userinfo 有属性是 var，直接判断不稳定，所以不会跳过
// userinfo 属性都是 val，再看包装类型 Tag 是不是 @Stable 的,是的话 UserInfo就是稳定的
// tag没有@stable 的话就判断是不是都是 val，如果都是，UserInfo 就是稳定的

@Stable
data class Tips5aTag(
    val id: Int,
    val name: String,
    val image: String,
)