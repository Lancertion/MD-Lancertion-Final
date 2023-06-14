package com.app.lancertion.data.remote.dto

import com.app.lancertion.domain.model.Register

data class RegisterDto(
    val `data`: Data,
    val message: String?,
    val success: Int
)

fun RegisterDto.toRegister(): Register {
    return Register(
        success = success,
        message = message
    )
}