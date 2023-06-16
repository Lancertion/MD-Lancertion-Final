package com.app.lancertion.domain.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.lancertion.data.remote.dto.GetUsersDto
import com.app.lancertion.data.remote.dto.LoginDto
import com.app.lancertion.data.remote.dto.RegisterDto
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.data.remote.request.RegisterBody
import com.app.lancertion.domain.model.User

interface LancertionAuthRepository {

    suspend fun login(body: LoginBody): LoginDto

    suspend fun create(body: RegisterBody): RegisterDto

    suspend fun getUsers(token: String): GetUsersDto

    suspend fun save(user: User)

    suspend fun load(): Preferences

    companion object {
        private const val FULL_NAME = "full_name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val TOKEN = "token"
        val fullName = stringPreferencesKey(FULL_NAME)
        val email = stringPreferencesKey(EMAIL)
        val password = stringPreferencesKey(PASSWORD)
        val token = stringPreferencesKey(TOKEN)
    }
}