package com.example.composetutorial.presentation.feature.tips_01

import android.util.Log
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun Tips01Screen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        val (count, setValue) = remember {
            mutableIntStateOf(0)
        }

        Column {
            Button(interactionSource = NoRippleInteractionSource(), onClick = {
                setValue(count + 1)
            }) {
                Text("Change name")
            }
            Text(text = "Count: $count")
            BadBookingList(count)
            GoodBookingList(count)
        }
    }
}


@Composable
fun BadBookingList(count: Int) {
    val scope = rememberCoroutineScope()
    var bookings by remember {
        mutableStateOf<List<Booking>>(emptyList())
    }

    // 这里不能这样，因为这是一个挂起函数，不能直接调用，
    // Compose 会重复调用多次，应该写到 LunchEffect 或者DisposableEffect里面
    scope.launch {
        bookings = loadingBookings()
        Log.d("lilili-tips-01", "scope.launch")
    }
    LazyColumn {
        items(bookings) { booking ->
            BookingItem(booking) {
                Text(text = count.toString())
            }
        }
    }

}

@Composable
fun GoodBookingList(count: Int) {
    val scope = rememberCoroutineScope()
    var bookings by remember {
        mutableStateOf<List<Booking>>(emptyList())
    }
    LaunchedEffect(Unit) {
        bookings = loadingBookings()
        Log.d("lilili-tips-01", "LaunchedEffect")
    }

    LazyColumn {
        items(bookings) { booking ->
            BookingItem(booking) {
                Text(text = count.toString())
            }
        }
    }

}


private fun loadingBookings(): List<Booking> {
    return listOf(
        Booking(
            id = 1,
            name = "Andy",
            date = "2021-09-01",
            time = "10:00",
            status = "Booked",
        ),
        Booking(
            id = 2,
            name = "Bob",
            date = "2021-09-02",
            time = "11:00",
            status = "Booked",
        ),
        Booking(
            id = 3,
            name = "Cindy",
            date = "2021-09-03",
            time = "12:00",
            status = "Booked",
        ),
    )
}

@Composable
fun BookingItem(booking: Booking, content: @Composable () -> Unit = {}) {
    Text(text = "Booking: ${booking.name}")
    content()
}


data class Booking(
    val id: Int,
    val name: String,
    val date: String,
    val time: String,
    val status: String,
)


class NoRippleInteractionSource() : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}