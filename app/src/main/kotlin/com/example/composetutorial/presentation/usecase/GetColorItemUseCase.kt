package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.data.local.ColorItemDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetColorItemUseCase() : KoinComponent {
    private val colorItemDataSource : ColorItemDataSource by inject()
    suspend operator fun invoke() = colorItemDataSource.getColorList()
}