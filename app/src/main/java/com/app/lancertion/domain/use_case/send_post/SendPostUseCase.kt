package com.app.lancertion.domain.use_case.send_post

import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.SendPostDto
import com.app.lancertion.data.remote.request.PostBody
import com.app.lancertion.domain.repository.LancertionCommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendPostUseCase @Inject constructor(
    private val repository: LancertionCommunityRepository
) {
    operator fun invoke(token: String, body: PostBody): Flow<Resource<SendPostDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.sendPost("Bearer $token", body)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}