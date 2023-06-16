package com.app.lancertion.domain.model

data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val token: String,
    val id: Int
)
