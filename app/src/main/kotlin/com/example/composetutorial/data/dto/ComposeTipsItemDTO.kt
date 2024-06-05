package com.example.composetutorial.data.dto

import kotlinx.serialization.Serializable

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
@Serializable
data class ComposeTipsItemDTO(val id:String, val path: String, val desc: String)

