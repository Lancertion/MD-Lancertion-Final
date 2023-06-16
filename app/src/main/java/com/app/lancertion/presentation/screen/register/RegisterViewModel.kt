package com.app.lancertion.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.request.RegisterBody
import com.app.lancertion.domain.use_case.auth_register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _dialogOpen = MutableStateFlow(false)
    val dialogOpen: StateFlow<Boolean> = _dialogOpen.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _registerState = mutableStateOf(RegisterState())
    private val registerState: State<RegisterState> = _registerState

    fun setName(name: String) {
        _uiState.value = uiState.value.copy(name = name)
    }

    fun setEmail(email: String) {
        _uiState.value = uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _uiState.value = uiState.value.copy(password = password)
    }

    fun closeDialog() {
        _uiState.value = uiState.value.copy("", "", "", "")
        _dialogOpen.value = false
    }

    fun openDialog() {
        _dialogOpen.value = true
    }

    fun register() {
        val registerBody = RegisterBody(
            fullName = uiState.value.name.trim(),
            email = uiState.value.email.trim(),
            password = uiState.value.password
        )

        registerUseCase(registerBody).onEach {result ->
            when(result) {
                is Resource.Success -> {
                    _registerState.value = registerState.value.copy(
                        data = result.data,
                        isLoading = false
                    )
                    openDialog()
                }
                is Resource.Error -> {
                    if(result.message == "HTTP 400 ") {
                        setError("400")
                    } else if(result.message == "HTTP 409 ") {
                        setError("409")
                    }
                    _registerState.value = registerState.value.copy(
                        isLoading = false,
                        error = result.message.toString()
                    )
                }
                is Resource.Loading -> {
                    _registerState.value = registerState.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setError(code: String) {
        _errorMessage.value = when(code) {
            "400" -> "Pendaftaran gagal, pastikan nama, email, dan password Anda sudah terisi dengan benar"
            "409" -> "Email sudah digunakan"
            else -> ""
        }
        _isError.value = true
    }

    fun unsetError() {
        _isError.value = false
    }

}