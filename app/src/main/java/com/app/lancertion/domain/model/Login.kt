package com.app.lancertion.domain.model

data class Login (
    val message: String?,
    val success: Int,
    val token: String?,
    val data: String? = null
)