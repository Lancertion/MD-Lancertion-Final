package com.app.lancertion.domain.use_case.get_posts

import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.PostDto
import com.app.lancertion.data.remote.dto.toListPost
import com.app.lancertion.domain.repository.LancertionCommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: LancertionCommunityRepository
) {

    operator fun invoke(token: String): Flow<Resource<List<PostDto>>> = flow {
        try {
            emit(Resource.Loading())
            val posts = repository.getPosts("Bearer $token").toListPost()
            emit(Resource.Success(posts))
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}