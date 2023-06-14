package com.app.lancertion.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.lancertion.data.remote.LancertionAuthApi
import com.app.lancertion.data.remote.dto.GetUsersDto
import com.app.lancertion.data.remote.dto.LoginDto
import com.app.lancertion.data.remote.dto.RegisterDto
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.data.remote.request.RegisterBody
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.repository.LancertionAuthRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class LancertionAuthRepositoryImpl @Inject constructor(
    private val api: LancertionAuthApi,
    private val dataStore: DataStore<Preferences>
): LancertionAuthRepository {
    override suspend fun login(body: LoginBody): LoginDto {
        return api.login(body)
    }

    override suspend fun create(body: RegisterBody): RegisterDto {
        return api.create(body)
    }

    override suspend fun getUsers(token: String): GetUsersDto {
        return api.getUsers(token)
    }

    override suspend fun save(user: User) {
        dataStore.edit {preference ->
            preference[fullName] = user.fullName
            preference[email] = user.email
            preference[password] = user.password
            preference[token] = user.token
        }
    }

    override suspend fun load(): Preferences {
        return dataStore.data.first()
    }


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