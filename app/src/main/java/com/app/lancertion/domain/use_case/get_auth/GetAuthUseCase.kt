package com.app.lancertion.domain.use_case.get_auth

import com.app.lancertion.data.repository.LancertionAuthRepositoryImpl
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.repository.LancertionAuthRepository
import java.lang.Exception
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(
    private val repository: LancertionAuthRepository
) {

    suspend fun getFullName(): String? = try {
        repository.load()[LancertionAuthRepositoryImpl.fullName]
    } catch (e: Exception) {
        null
    }

    suspend fun getToken(): String? = try {
        repository.load()[LancertionAuthRepositoryImpl.token]
    } catch (e: Exception) {
        null
    }

    suspend fun getId(): Int? = try {
        repository.load()[LancertionAuthRepositoryImpl.id]
    } catch (e: Exception) {
        null
    }

    suspend fun save(user: User) = repository.save(user)

}