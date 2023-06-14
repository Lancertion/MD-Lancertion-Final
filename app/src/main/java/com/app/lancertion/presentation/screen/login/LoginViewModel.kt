package com.app.lancertion.presentation.screen.login

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.domain.use_case.auth_login.LoginUseCase
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getAuthUseCase: GetAuthUseCase
): ViewModel()  {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun setEmail(email: String) {
        _uiState.value = uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _uiState.value = uiState.value.copy(password = password)
    }

    fun login() {
        val loginBody = LoginBody(
            email = uiState.value.email,
            password = uiState.value.password
        )
        loginUseCase(loginBody).onEach {result ->
            when(result) {
                is Resource.Success -> {
                    Log.d("login", "sukses")
                    result.data?.let {user ->
                        getAuthUseCase.save(user)
                        _loginState.value = loginState.value.copy(
                            isLoading = false,
                            data = user
                        )
                    }
                }
                is Resource.Error -> {
                    Log.d("login", "${result.message.isNullOrBlank()}")
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        isError = true,
                        error = result.message.toString()
                    )
                }
                is Resource.Loading -> {
                    Log.d("login", "loading")
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}