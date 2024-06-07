package com.example.composetutorial.presentation.feature.tips_06

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 *
 * @author: est8
 * @date: 2024/6/7
 */
class Tips06ViewModel(val handle: SavedStateHandle) : ViewModel() {
    private val _counter = MutableStateFlow(0)

    // stateIn 函数会将 Flow 转换为 StateFlow，并且它还会在给定的 CoroutineScope 中启动一个协程来收集这个 Flow
    val stateInCounter =
        _counter.onEach { saveCounterToDb(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), 0)

    val asStateCounter = _counter.asStateFlow()

    fun addOneCounter() {
        viewModelScope.launch {
            _counter.value += 1
        }
    }

    fun startCounter() {
        viewModelScope.launch {
            repeat(100) {
                _counter.value += 1
                delay(1000)
            }
        }
    }

    private fun saveCounterToDb(it: Int) {
        // save to db
        Log.d("Tips06ViewModel", "saveCounterToDb: $it")
    }
}
