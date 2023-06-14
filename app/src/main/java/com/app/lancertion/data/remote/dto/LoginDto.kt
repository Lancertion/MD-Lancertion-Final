package com.app.lancertion.data.remote.dto

import com.app.lancertion.domain.model.Login

data class LoginDto(
    val message: String?,
    val success: Int,
    val token: String?,
    val data: String?
)

fun LoginDto.toLogin(): Login {
    return Login(
        message = message,
        success = success,
        token = token,
        data = data
    )
}