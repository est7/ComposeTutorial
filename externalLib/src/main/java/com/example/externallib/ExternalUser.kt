package com.example.externallib

/**
 *
 * @author: est8
 * @date: 2024/6/6
 */

data class ExternalValUser(
    val id: String, val age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)

data class ExternalVarUser(
    val id: String, var age: Int, val name: String, val isAdmin: Boolean, val profilePictureUrl: String
)
