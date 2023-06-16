package com.app.lancertion.data.remote.dto

import com.app.lancertion.domain.model.User

data class UserDto(
    val email: String,
    val fullName: String,
    val id: Int,
    val number: Long
)

fun UserDto.toUser(): User {
    return User(
        fullName = fullName,
        email = email,
        password = "",
        token = "",
        id = id
    )
}