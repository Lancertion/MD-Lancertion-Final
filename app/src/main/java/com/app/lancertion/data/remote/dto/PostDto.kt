package com.app.lancertion.data.remote.dto

import com.app.lancertion.domain.model.Post

data class PostDto(
    val content: String,
    val pengirim: String,
    val post_id: Int,
    val tanggal: String,
    val user_id: Int
)

fun PostDto.toPost(): Post {
    return Post(
        content = content,
        pengirim = pengirim,
        post_id = post_id,
        tanggal = tanggal,
        user_id = user_id
    )
}