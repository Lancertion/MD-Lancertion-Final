package com.app.lancertion.data.remote

import com.app.lancertion.data.remote.dto.GetUsersDto
import com.app.lancertion.data.remote.dto.LoginDto
import com.app.lancertion.data.remote.dto.RegisterDto
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.data.remote.request.RegisterBody
import com.app.lancertion.domain.model.Login
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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

    @GET("https://lancertion-project.et.r.appspot.com/api/users/allusers")
    suspend fun getUsers(
        @Header("Authorization") token: String
    ): GetUsersDto

}

