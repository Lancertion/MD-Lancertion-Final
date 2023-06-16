package com.app.lancertion.data.remote.dto

data class GetCommentsDto(
    val `data`: List<CommentDto>,
    val success: Int
)

fun GetCommentsDto.toList(): List<CommentDto> {
    return data
}