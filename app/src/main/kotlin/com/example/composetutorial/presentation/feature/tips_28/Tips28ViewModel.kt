package com.example.composetutorial.presentation.feature.tips_28

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.presentation.usecase.GetColorItemUseCase
import com.example.composetutorial.utils.MyDateUtils
import com.example.composetutorial.utils.ResourceProviderUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 *
 * @author: est8
 * @date: 2024/6/7
 */
class Tips28ViewModel(
    val handle: SavedStateHandle,
    private val getColorItemUseCase: GetColorItemUseCase,
    private val resourceProvider: ResourceProviderUtils,
) : ViewModel() {
    private val _tips28UiState: MutableStateFlow<Tips28UiState> =
        MutableStateFlow(Tips28UiState.Loading)
    val tips28UiState = _tips28UiState.asStateFlow()

}
sealed interface Tips28UiState {
    data object Loading : Tips28UiState
    data object Error : Tips28UiState
    data class Success(val data: List<Color>) : Tips28UiState

}