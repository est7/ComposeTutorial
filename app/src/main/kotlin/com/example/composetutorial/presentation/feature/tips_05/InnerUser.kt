package com.example.composetutorial.presentation.feature.tips_05

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 *
 * @author: est8
 * @date: 2024/6/7
 */

data class InnerValUser(
    val id: String, val age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)

data class InnerVarUser(
    val id: String, var age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)

@Immutable
data class InnerVarImmutableUser(
    val id: String, var age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)

@Immutable
data class InnerValImmutableUser(
    val id: String, val age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)

@Stable
data class InnerVarStableUser(
    val id: String, var age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)

@Stable
data class InnerValStableUser(
    val id: String, val age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)
