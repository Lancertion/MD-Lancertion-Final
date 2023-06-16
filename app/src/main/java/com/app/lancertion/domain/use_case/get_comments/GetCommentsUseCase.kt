package com.app.lancertion.domain.use_case.get_comments

import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.CommentDto
import com.app.lancertion.data.remote.dto.toList
import com.app.lancertion.domain.repository.LancertionCommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: LancertionCommunityRepository
) {

    operator fun invoke(token: String, id: Int): Flow<Resource<List<CommentDto>>> = flow {
        try {
            emit(Resource.Loading())
            val comments = repository.getCommentsById("Bearer $token", id).toList()
            emit(Resource.Success(comments))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}