package com.example.composetutorial.presentation.feature.tips_05

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetutorial.presentation.feature.tips_01.NoRippleInteractionSource
import com.example.externallib.ExternalValUser
import com.example.externallib.ExternalVarUser


var string = "string"
val mutableString = MutableString("MutableString")
var valImmutableString = ValImmutableString("ValImmutableString")
val immutableString = ImmutableString("ImmutableString")
val stableMutableString = StableMutableString("StableMutableString")
val observableMutableString = ObservableMutableString().apply { data.value = "ObservableMutableString" }


var externalVarUser = ExternalVarUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)
var externalValUser = ExternalValUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)

var innerValUser = InnerValUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)
val innerVarUser = InnerVarUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)
var immutableValInnerUser = InnerValImmutableUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)
val immutableVarInnerUser = InnerVarImmutableUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)
var stableValInnerUser = InnerValStableUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)
val stableVarInnerUser = InnerVarStableUser(
    id = "1", age = 18, name = "John Doe", isAdmin = true, profilePictureUrl = "https://example.com/profile.jpg"
)


@Composable
fun Tips05Screen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
        ) {
            // TestImmutableAndStableString()
            TestImmutableAndStableUserInfo()
        }
    }
}

@Composable
fun TestImmutableAndStableUserInfo() {
    val count = remember { mutableIntStateOf(0) }

    Button(
        interactionSource = NoRippleInteractionSource(),
        onClick = {
            count.intValue += 1

            //这里执行/注释，可以看出来 ExternalValUser即使是稳定的，也会 recompose,跟InnerValUser不同表现
            // updateUser(count)

        }) {
        Text(text = "Click me")
    }
    Text(text = "Count:${count.intValue}")

    ShowExternalValUser(externalValUser)
    ShowExternalVarUser(externalVarUser)
    ShowInnerValUser(innerValUser)
    ShowInnerVarUser(innerVarUser)
    ShowImmutableValInnerUser(immutableValInnerUser)
    ShowImmutableVarInnerUser(immutableVarInnerUser)
    ShowStableValInnerUser(stableValInnerUser)
    ShowStableVarInnerUser(stableVarInnerUser)

}

@Composable
fun ShowStableVarInnerUser(stableVarInnerUser: InnerVarStableUser) {
    Text(text = "Hello ${stableVarInnerUser.name}")
    Text(text = "Age: ${stableVarInnerUser.age}")
}

@Composable
fun ShowStableValInnerUser(stableValInnerUser: InnerValStableUser) {
    Text(text = "Hello ${stableValInnerUser.name}")
    Text(text = "Age: ${stableValInnerUser.age}")
}

@Composable
fun ShowImmutableVarInnerUser(immutableVarInnerUser: InnerVarImmutableUser) {
    Text(text = "Hello ${immutableVarInnerUser.name}")
    Text(text = "Age: ${immutableVarInnerUser.age}")
}

@Composable
fun ShowInnerVarUser(innerVarUser: InnerVarUser) {
    Text(text = "Hello ${innerVarUser.name}")
    Text(text = "Age: ${innerVarUser.age}")
}

@Composable
fun ShowImmutableValInnerUser(immutableValInnerUser: InnerValImmutableUser) {
    Text(text = "Hello ${immutableValInnerUser.name}")
    Text(text = "Age: ${immutableValInnerUser.age}")
}

@Composable
fun ShowInnerValUser(innerValUser: InnerValUser) {
    Text(text = "Hello ${innerValUser.name}")
    Text(text = "Age: ${innerValUser.age}")

}

@Composable
fun ShowExternalVarUser(externalVarUser: ExternalVarUser) {
    Text(text = "Hello ${externalVarUser.name}")
    Text(text = "Age: ${externalVarUser.age}")

}

@Composable
fun ShowExternalValUser(externalValUser: ExternalValUser) {
    Text(text = "Hello ${externalValUser.name}")
    Text(text = "Age: ${externalValUser.age}")
}


@Composable
private fun TestImmutableAndStableString() {
    val count = remember { mutableIntStateOf(0) }

    Button(onClick = {
        count.intValue += 1

        updateString(count)

    }) {
        Text(text = "Click me")
    }
    Text(text = "Count:${count.intValue}")

    ShowString(string)
    ShowMutableString(mutableString)
    ShowValImmutableString(valImmutableString)
    ShowImmutableString(immutableString)
    ShowStableMutableString(stableMutableString)
    ShowObservableMutableString(observableMutableString)
}

fun updateUser(count: MutableIntState) {/*
        externalUser = externalUser.copy(
            age = count.value
        )
        innerUser = innerUser.copy(
            age = count.value
        )
        immutableInnerUser = immutableInnerUser.copy(
            age = count.value
        )
        stableInnerUser = stableInnerUser.copy(
            age = count.value
        )
    */
    externalVarUser.age = count.intValue
    innerVarUser.age = count.intValue
    immutableVarInnerUser.age = count.intValue
    stableVarInnerUser.age = count.intValue

    externalValUser = externalValUser.copy(age = count.intValue)
    innerValUser = innerValUser.copy(age = count.intValue)
    immutableValInnerUser = immutableValInnerUser.copy(age = count.intValue)
    stableValInnerUser = stableValInnerUser.copy(age = count.intValue)


}

fun updateString(count: MutableIntState) {

    string = "string" + count.intValue

    mutableString.data = "MutableString" + count.intValue

    valImmutableString = valImmutableString.copy(data = "ValImmutableString" + count.intValue)

    immutableString.data = "ImmutableString" + count.intValue

    stableMutableString.data = "StableMutableString" + count.intValue

    observableMutableString.data.value = "ObservableMutableString" + count.intValue
}

// 1. 不可变类型：String
@Composable
fun ShowString(string: String) {
    Text(text = "Hello $string")
}

// 2. 可变类型：有可变的属性
data class MutableString(var data: String)

@Composable
fun ShowMutableString(string: MutableString) {
    Text(text = "Hello ${string.data}")
}

// 3. 不可变类型：成员属性全是 final
data class ValImmutableString(val data: String)

@Composable
fun ShowValImmutableString(string: ValImmutableString) {
    Text(text = "Hello ${string.data}")
}

@Immutable
data class ImmutableString(var data: String)

@Composable
fun ShowImmutableString(string: ImmutableString) {
    Text(text = "Hello ${string.data}")
}

// 4. 可变类型加 @Stable 注解
@Stable
data class StableMutableString(var data: String)

@Composable
fun ShowStableMutableString(string: StableMutableString) {
    Text(text = "Hello ${string.data}")
}

// 5. 变化可被追踪
data class ObservableMutableString(
    val data: MutableState<String> = mutableStateOf(""),
)

@Composable
fun ShowObservableMutableString(string: ObservableMutableString) {
    Text(text = "Hello ${string.data.value}")
}



