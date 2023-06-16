package com.app.lancertion.domain.use_case.auth_login

import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.find
import com.app.lancertion.data.remote.dto.toLogin
import com.app.lancertion.data.remote.dto.toUser
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.repository.LancertionAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LancertionAuthRepository
) {
    operator fun invoke(body: LoginBody): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val login = repository.login(body).toLogin()

            if(login.success == 0) {
                throw Exception(login.data)
            } else {
                val token = login.token
                val users = repository.getUsers("Bearer $token")

                val user = users.find(body.email).toUser().copy(
                    token = token!!,
                    password = body.password
                )
                emit(Resource.Success(user))
            }


        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage as String))
        }
    }
}