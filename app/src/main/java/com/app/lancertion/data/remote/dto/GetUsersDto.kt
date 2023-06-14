package com.app.lancertion.data.remote.dto

import com.app.lancertion.domain.model.User

data class GetUsersDto(
    val `data`: List<UserDto>,
    val success: Int
)

fun GetUsersDto.getFullName(email: String): String {
    return data.find { user ->
        user.email == email
    }?.fullName!!
}