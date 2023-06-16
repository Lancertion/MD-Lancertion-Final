package com.app.lancertion.domain.use_case.send_comment

import android.util.Log
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.SendPostDto
import com.app.lancertion.data.remote.request.CommentBody
import com.app.lancertion.domain.repository.LancertionCommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendCommentUseCase @Inject constructor(
    private val repository: LancertionCommunityRepository
) {
    operator fun invoke(token: String, body: CommentBody, id: Int): Flow<Resource<SendPostDto>> = flow {
        try {
            emit(Resource.Loading())
            Log.d("result", body.toString())
            val result = repository.sendComment("Bearer $token", body, id)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}