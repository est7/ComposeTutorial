Before Compose strong skipping mode, lambdas capturing unstable values would never skip. Strong skipping mode fixes that by remembering all those lambdas automatically.

More details and examples here

[强烈的跳过模式  |  Jetpack Compose  |  Android Developers](https://developer.android.com/develop/ui/compose/performance/stability/strongskipping?hl=zh-cn#lambda-memoization)

`LaunchedEffect(xxx) { ... }` is kind of pointless and is basically just a helper function, and is equivalent to`LaunchedEffect(xxx) { ... }` 有点毫无意义，基本上只是一个辅助函数，相当于

```kotlin
val scope = rememberCoroutineScope()
DisposableEffect(xxx) {
    val job = scope.launch {
         ...
    }
    onDispose {
         job.cancel()
    }
}
```

# **不要在 non-compose 代码中调用 composable functions**

**每当可组合项被重组（这意味着该可组合项使用的状态值发生更改）时，可组合函数就会再次被调用。一般来说，可组合函数可以在任何时间以任何顺序调用。如果您现在将非 Compose 代码放入可组合函数中 - 这意味着在带有 @Compose 注释的函数中调用不带 @Compose 注释的函数 - 该函数可能会执行很多次。**

# 不要使用mutablelist 作为 state

使状态成为不可变列表。每当状态完全更改并替换为新值时，Compose 将检测更改并重新组合使用该状态的任何可组合项。您可以像操作可变列表一样操作不可变列表，只是每次更改都会创建一个新的列表实例。

# **要根据现实情况把 State 记忆**

在 Compose 中创建状态的一种非常常见的方法是使用 Remember。这本身是正确的，因为它不会因为记忆而在每次重组时重新创建状态。但是，在实际的应用程序中，如果这不会适得其反，您应该三思而后行，因为只要不涉及配置更改或进程死亡，记住只会在重组期间缓存值。看看这个：

****

# 在LazyColumn 中记得使用 key

使用 lambda 键告诉 Lazy Column 如何唯一标识每个项目，例如通过每个注释的 ID。这样，惰性列将仅重组实际更改的项目。

# 不要在外部 module 里使用 unstable class

这里还要注意，目前即使在外部 module 中使用 stable 注解，还是会被认为是不稳定的

Compose有稳定和不稳定的概念，简而言之，如果一个类的所有这些条件都为真，那么这个类就会被Compose编译器标记为稳定：

1. equals()的结果对于两个实例总是会返回相同的结果
2. 当该类型的一个public属性发生变化时，会通知组件进行重组。
3. 所有公共属性类型都是稳定的。比如 Person 下有一个 public val tag：Tag，该 Tag类也应该是 stable 的

然而，许多人不知道的是：如果您使用不使用 Compose 的外部模块或库中的类，那么默认情况下这些类是不稳定的。

# 不要使用compostAsState()

在Compose中，我们可以使用compostAsState()函数将Flow转换为Compose状态。然而，在Android项目中最好避免这个函数，因为它不知道任何关于你的活动的生命周期。这意味着当您的活动在后台进行时，即使用户看不到UI上的更改，后台中 flow仍将被执行。

请注意，这不会影响使用asStateFlow()创建的StateFlow，而只影响使用StateIn()创建的StateFlow。

# 记得在在Graphicslayer之内进行动画变换

只要RotationRatio改变(每秒多次)，可合成的长方体将被重新组合，因为它使用改变的状态(这是旋转比率)。

如果合成体的外观不变，则不应对其进行重组。旋转后的合成物看起来完全一样，只是稍微旋转了一下。因此，您可以使用GraphicsLayer修改器，该修改器将具有相同的效果，但不会导致数百次重组。因此：如果剪裁、变换或Alpha发生更改请使用GraphicsLayer

# 不要使用注入工具在screen 级别的compose 组件上创建viewmodel

Viewmodel是在Android上存储和管理状态的典型方式。不过，您会在网上看到许多实例化和使用ViewModel的示例，如下所示：

一旦您的ViewModel在其构造函数中注入了依赖项，这将中断预览和独立的Ul测试，因为此屏幕不能单独使用。原因是，为了使用该屏幕，它总是需要应用程序的其余部分和Dagger-Hilt模块的上下文来创建ViewModel的实例。

# **不要在子可组合项中设置扩展尺寸**

**Compose中的Modifiers很强大，但它们也经常被滥用。Compose的一个主要目标应该是使您的组件尽可能地可重用。但是，您经常会看到这样的情况：**

```kotlin
@Composable
fun Tips09BadMyButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, modifier = modifier
            .clip(RoundedCornerShape(100))
            .fillMaxWidth()
    ) {
        Text(text = "Cool button")
    }
}

```

我们建议避免在可重复使用的可组合对象的最外层使用扩展大小修改器。在左侧的示例中，您可以看到在设置了样式的按钮上使用了fulMaxWidth()修饰符，该按钮可能会在整个应用程序中重复使用。但是，fulMaxWidth()修饰符强制每个按钮始终填充父Composable的整个宽度，从而防止您在没有解决办法的情况下将两个按钮紧挨着放置。

最好不要在可重用可组合组件的根修饰符中硬编码这样的大小，而是从外部传递它们以保持灵活性。

# 记得在重的 computations  compose中使用 remember

remember 函数可用于在重新组合时缓存计算值。非常有用。对吗？然而，很多人不使用它来进行繁重的计算，这会导致您的应用程序和Ul的性能较低：

最好使用带有key的remember，以便在密钥更改时重新计算它。请注意，这只是一个示例来说明这一点，像解密这样的事情应该在单独的类中发生，并从您的ViewModel中调用。这也可以应用于与繁重的UL相关的计算(例如，评估复杂条件)

# 不要过度使用硬编码 *.dp

有时，我们有一个固定大小的composable。但通常我们也不这样做。在这些情况下，避免使用硬编码的dp值来表示可组合元素的尺寸，而使用FillMaxSize()等修饰符切换到相对大小。Weight()或WidthIn()。

```kotlin
@Composable
fun Tips12Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column() {
            Button(
                onClick = {}, modifier = Modifier
                    .widthIn(400.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Button 1")
            }

            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Button 1")
            }
        }
    }
}

```

此文本字段在每个屏幕大小上的宽度都将为300dp。仅仅因为它在你测试过的一台设备上看起来很好，并不意味着它在更小、更大或平板电脑设备上也能看起来很好。

相反，使用相对大小(您也可以将其与左侧的最大固定大小组合在一起)。这个文本字段现在将在手机上填满整个宽度，而在平板电脑上不会在整个屏幕上伸展，这在平板电脑上会看起来很奇怪，它将有一个固定的400 DP大小。

# 不要忘记设置触摸目标的大小

在创建可点击的合成物时，我们必须注意触摸目标的大小。如果您的Composable是一个小的，用户可能很难点击它，因为它没有很大的区域。在创建这种可组合组件时，请考虑这一点。

**可以使用Modifier.pointerInput 调整点击区域**

# 记得检查 fragment 中的 decomposition 策略

Compose提供了与XML的很好的互操作性。但是，当在片段中使用ComposeView时，请确保设置正确的视图分解策略，以确保正确地处理组合。默认情况下，当底层ComposeView从窗口分离时，合成就会被释放，这是纯Jetpack Compose应用所需要的。但是，在增量添加Compose时，这可能不是您想要的。

`ViewCompositionStrategy.Default` 会在底层 `ComposeView` 从窗口分离时释放组合，

### [DisposeOnViewTreeLifecycleDestroyed](https://link.juejin.cn/?target=https%3A%2F%2Fdeveloper.android.com%2Freference%2Fkotlin%2Fandroidx%2Fcompose%2Fui%2Fplatform%2FViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

当`ViewTreeLifecycleOwner`提供的`Lifecycle`被销毁时，组合将被释放（译者注：viewTree提供的`Lifecycle`和组件提供的`Lifecycle`生命周期不一致，例如`Fragment#mView`和`Fragment`提供的Lifecycle不一样，这里不展开，请读者自行查阅）。

使用场景：

- 作为`Fragment`中的`View`的`ComposeView`。
- 生命周期未知的`View`中的`ComposeView`。

将视图分解策略设置为[DisposeOnViewTreeLifecycleDestroyed](https://link.juejin.cn/?target=https%3A%2F%2Fdeveloper.android.com%2Freference%2Fkotlin%2Fandroidx%2Fcompose%2Fui%2Fplatform%2FViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)的策略，将组合与 Fragment 的生命周期联系起来。请注意，这仅在片段中是必需的。

[juejin.cn](https://juejin.cn/post/7246304095375573051)

# 不要把入参命名方法混用

在Ul开发中，有两种方法可以创建和命名你的状态：

1.根据它将对Ul产生的影响来命名它；

2.根据它代表的逻辑行为来命名它；

选择哪种方法取决于你，但保持一致很重要。看看这些例子：

```kotlin
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

```

IsProgressBarVisible基于(1)命名，因为它准确地描述了它将对Ul产生的影响(它将显示或隐藏一个进度条)。另一方面，isLoginFailed描述了一种逻辑行为，它不会直接揭示它将对Ul产生什么样的影响。上面的例子使用了不一致的命名，因为它使用了两种方法来进行状态命名。

# 不要忘记将colums/row设置为可滚动

有时，您的屏幕由许多可组合的放置在普通列中。尽管它是固定大小的固定数量的合成物，但您应该考虑将此屏幕设置为可滚动。仅仅因为你的测试设备的屏幕上所有的东西都适合，并不意味着所有的东西都可以在更小的设备上容纳。因此，确保屏幕一旦达到合理大小，就保持可滚动。

# 最好在 可组合项中写明 Lambda 的名字

Lambda 在Jetpack Compose中扮演主要角色，以对用户操作和提升状态做出反应。然而，Kotlin内联函数参数列表的最后一个lambda函数的功能可能会使您的代码不可读。

# 避免错误使用使用rememberCoroutineScope

使用rememberCoroutineScope()。我们可以得到一个知道当前重组范围的协程作用域。然而，许多人错误地使用它：

不要使用可组合协程作用域来执行与Ul无关的挂起函数。您的ViewModel不应该向Ul公开suspend fanc，因为这个协程作用域将在配置更改(如屏幕旋转)后被取消。在这种情况下，登录呼叫也将被取消。

好的做法是在viewModelScope协程中启动这样的函数，并在挂起的调用完成时更新状态，例如在左侧。仅使用可组合协程作用域执行与URL相关的挂起功能，如触发动画或显示Snackbar。

# 涉及频繁变化的状态传递给子 composable 时，避免不必要的重新组合（recomposition）。

当用户滚动时，scrollState 将会改变，这将触发 ListItem 的重新组合，因为状态发生了变化。

如果将状态作为 lambda 的结果传递，你可以使 ListItem composable 跳过组合阶段，直接进入布局阶段，因为 lambda 本身不会改变，因此不会触发重新组合。这仅在将状态传递给另一个 composable 函数时才相关。如果你内联 ListItem 的内容在 for 循环中，则不需要将其变为 lambda 函数。

# 如果可以，记得使用scaffold 的 paddingValues

scaffold是一种布局，可帮助您将常见的 Android Ul 组件正确放置在屏幕上。例如drawers、snackbars或toolbar。然而，一个常见的错误是忽略 Scaffold 提供的内容 Padding 参数：

通过将paddingValues应用于Box Composable，可以确保任何Box内容都不会隐藏在Scaffold Composables之后。

# 不要在可组合函数中使用 return

避免在可组合函数中使用 return 关键字，因为这可能会在跳过组合阶段时导致未定义的行为。

最好使用 let 或 if 语句进行空检查。

# `利用 animate*AsState 系列函数为状态变化添加动画效果，提升用户体验。`

# `使用 `produceState` 将异步数据转换为 Compose 状态以简化数据加载`

# `利用 derivedStateOf 创建依赖于其他状态的状态，减少不必要的重组。`

# `利用 CompositionLocal 提供跨整个应用程序的依赖注入。`