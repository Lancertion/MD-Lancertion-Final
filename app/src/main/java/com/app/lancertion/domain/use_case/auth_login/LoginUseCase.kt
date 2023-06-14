package com.app.lancertion.domain.use_case.auth_login

import android.util.Log
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.LoginDto
import com.app.lancertion.data.remote.dto.getFullName
import com.app.lancertion.data.remote.dto.toLogin
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.domain.model.Login
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
            Log.d("logindto", login.toString())

            if(login.success == 0) {
                throw Exception(login.data)
            } else {
                val token = login.token
                val users = repository.getUsers("Bearer $token")
                val fullName = users.getFullName(body.email)
                val user = User(
                    fullName = fullName,
                    email = body.email,
                    password = body.password,
                    token = token!!
                )

                Log.d("login user", user.toString())

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