package com.app.lancertion.data.remote.dto

data class GetUsersDto(
    val `data`: List<UserDto>,
    val success: Int
)

fun GetUsersDto.find(email: String): UserDto {
    return data.find {user ->
        user.email == email
    }!!
}