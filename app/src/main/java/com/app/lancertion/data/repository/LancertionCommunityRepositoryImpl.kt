package com.app.lancertion.data.repository

import com.app.lancertion.data.remote.LancertionAuthApi
import com.app.lancertion.data.remote.dto.DeletePostDto
import com.app.lancertion.data.remote.dto.GetCommentsDto
import com.app.lancertion.data.remote.dto.GetPostsDto
import com.app.lancertion.data.remote.dto.SendPostDto
import com.app.lancertion.data.remote.request.CommentBody
import com.app.lancertion.data.remote.request.PostBody
import com.app.lancertion.domain.repository.LancertionCommunityRepository
import javax.inject.Inject

class LancertionCommunityRepositoryImpl @Inject constructor(
    private val api: LancertionAuthApi
): LancertionCommunityRepository {

    override suspend fun getPosts(token: String): GetPostsDto {
        return api.getPosts(token)
    }

    override suspend fun getCommentsById(token: String, id: Int): GetCommentsDto {
        return api.getComments(token, id)
    }

    override suspend fun sendPost(token: String, body: PostBody): SendPostDto {
        return api.sendPost(token, body)
    }

    override suspend fun sendComment(token: String, body: CommentBody, id: Int): SendPostDto {
        return api.sendComment(token, body, id)
    }

    override suspend fun deletePost(token: String, id: Int): DeletePostDto {
        return api.deletePost(token, id)
    }

    override suspend fun deleteComment(token: String, id: Int): DeletePostDto {
        return api.deleteComment(token, id)
    }

}