package com.example.composetutorial.data.dto

import kotlinx.collections.immutable.ImmutableList

/**
 *
 * @author: est8
 * @date: 2024/6/18
 */
data class PagedData<T>(
    val data: ImmutableList<T>,
    val currentPage: Int,
    val hasMore: Boolean
)