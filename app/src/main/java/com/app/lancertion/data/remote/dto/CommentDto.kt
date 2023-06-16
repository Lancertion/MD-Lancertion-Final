package com.app.lancertion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CommentDto(
    val comment: String,
    @SerializedName("comment_id") val id: Int,
    val post_id: Int,
    val tanggal: String,
    val user_id: Int,
    val pengirim: String
)