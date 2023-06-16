package com.app.lancertion.data.remote

import com.app.lancertion.data.remote.dto.DeletePostDto
import com.app.lancertion.data.remote.dto.GetCommentsDto
import com.app.lancertion.data.remote.dto.GetPostsDto
import com.app.lancertion.data.remote.dto.GetUsersDto
import com.app.lancertion.data.remote.dto.LoginDto
import com.app.lancertion.data.remote.dto.RegisterDto
import com.app.lancertion.data.remote.dto.SendPostDto
import com.app.lancertion.data.remote.request.CommentBody
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.data.remote.request.PostBody
import com.app.lancertion.data.remote.request.RegisterBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface LancertionAuthApi {

    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    suspend fun login(
        @Body body: LoginBody
    ): LoginDto

    @POST("/api/users/create")
    suspend fun create(
        @Body body: RegisterBody
    ): RegisterDto

    @GET("/api/users/allusers")
    suspend fun getUsers(
        @Header("Authorization") token: String
    ): GetUsersDto

    @GET("/api/com/post")
    suspend fun getPosts(
        @Header("Authorization") token: String
    ): GetPostsDto

    @GET("/api/com/post/{id}/comment")
    suspend fun getComments(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GetCommentsDto

    @Headers("Content-Type: application/json")
    @POST("/api/com/post")
    suspend fun sendPost(
        @Header("Authorization") token: String,
        @Body body: PostBody
    ): SendPostDto

    @Headers("Content-Type: application/json")
    @POST("/api/com/post/{id}/comment")
    suspend fun sendComment(
        @Header("Authorization") token: String,
        @Body body: CommentBody,
        @Path("id") id: Int
    ): SendPostDto

    @DELETE("/api/com/deletePost/{id}")
    suspend fun deletePost(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeletePostDto

    @DELETE("/api/com/deleteComment/{id}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeletePostDto
}

