package com.example.composetutorial.data.local

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Random

/**
 *
 * @author: est8
 * @date: 2024/6/27
 */
class ColorItemDataSource {
    private val defaultList = mutableListOf(
        generateColor(),
        generateColor(),
        generateColor(),
        generateColor(),
    )

    fun getColorList(): Flow<Result<List<Color>>> = flow {
        delay(1000)
        val colorList = mutableListOf<Color>()
        // uncomment this if you want to simulate item being added during refresh
        // defaultList.add(0, generateColor())
        colorList.addAll(defaultList)
        emit(Result.success(colorList))
    }

    private fun generateColor(): Color {
        val rnd = Random()
        val color =
            android.graphics.Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return Color(color)
    }
}