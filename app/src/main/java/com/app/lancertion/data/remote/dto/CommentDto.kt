package com.app.lancertion.data.remote.dto

data class CommentDto(
    val comment: String,
    val id: Int,
    val post_id: Int,
    val tanggal: String,
    val user_id: Int
)