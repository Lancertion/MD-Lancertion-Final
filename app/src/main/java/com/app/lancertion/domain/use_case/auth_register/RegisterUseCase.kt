package com.app.lancertion.domain.use_case.auth_register

import android.util.Log
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.toRegister
import com.app.lancertion.data.remote.request.RegisterBody
import com.app.lancertion.domain.model.Register
import com.app.lancertion.domain.repository.LancertionAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: LancertionAuthRepository
) {
    operator fun invoke(body: RegisterBody): Flow<Resource<Register>> = flow {
        try {
            emit(Resource.Loading())
            Log.d("register anu", body.toString())
            val register = repository.create(body).toRegister()
            Log.d("result register", register.toString())
            if(register.success == 0) {
                throw Exception("sukses_nol")
            }
            emit(Resource.Success(register))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}