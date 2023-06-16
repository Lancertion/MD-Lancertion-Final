package com.app.lancertion.domain.repository

import com.app.lancertion.data.remote.dto.DeletePostDto
import com.app.lancertion.data.remote.dto.GetCommentsDto
import com.app.lancertion.data.remote.dto.GetPostsDto
import com.app.lancertion.data.remote.dto.SendPostDto
import com.app.lancertion.data.remote.request.CommentBody
import com.app.lancertion.data.remote.request.PostBody

interface LancertionCommunityRepository {

    suspend fun getPosts(token: String): GetPostsDto

    suspend fun getCommentsById(token: String, id: Int): GetCommentsDto

    suspend fun sendPost(token: String, body: PostBody): SendPostDto

    suspend fun sendComment(token: String, body: CommentBody, id: Int): SendPostDto

    suspend fun deletePost(token: String, id: Int): DeletePostDto

    suspend fun deleteComment(token: String, id: Int): DeletePostDto

}