package com.app.lancertion.data.remote
import com.app.lancertion.data.remote.dto.DiagnoseDto
import com.app.lancertion.data.remote.request.DiagnoseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LancertionDiagnoseApi {

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun diagnose(
        @Body body: DiagnoseBody
    ): DiagnoseDto

}