package com.example.composetutorial.data.dto

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
@Serializable
data class ComposeTipsItemDTO(val id:Int, val path: String, val desc: String)

