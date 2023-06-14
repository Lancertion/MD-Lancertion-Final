package com.app.lancertion.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.use_case.auth_login.LoginUseCase
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuthUseCase: GetAuthUseCase,
): ViewModel() {

    private val _isLogged = mutableStateOf<Boolean>(false)
    val isLogged: State<Boolean> = _isLogged

    private val _token = mutableStateOf<String>("")
    val token: State<String> = _token

    private val _name = mutableStateOf<String>("")
    val name: State<String> = _name

    init {
        refreshToken()
    }

    fun refreshToken() {
        viewModelScope.launch {
            _token.value = getAuthUseCase.getToken() ?: ""
            _name.value = getAuthUseCase.getFullName() ?: ""
        }
    }

    fun login() {
        _isLogged.value = true
    }

    fun logout() {
        viewModelScope.launch {
            val user = User("", "", "", "")
            getAuthUseCase.save(user)
            _isLogged.value = false
        }
    }

}