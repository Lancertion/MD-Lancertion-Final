package com.app.lancertion.data.remote.dto

data class GetPostsDto(
    val `data`: List<PostDto>,
    val success: Int
)

fun GetPostsDto.toListPost(): List<PostDto> {
    return data
}

//fun GetPostsDto.toPost(id: Int): Post {
//    return data.find {  post ->
//        post.post_id == id
//    }!!.toPost()
//}