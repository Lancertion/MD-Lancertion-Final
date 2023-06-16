package com.app.lancertion.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuthUseCase: GetAuthUseCase,
): ViewModel() {

    private val _isLogged = mutableStateOf(false)

    private val _token = mutableStateOf("")
    val token: State<String> = _token

    private val _userId = MutableStateFlow(0)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    init {
        refreshToken()
    }

    fun refreshToken() {
        viewModelScope.launch {
            _token.value = getAuthUseCase.getToken() ?: ""
            _name.value = getAuthUseCase.getFullName() ?: ""
            _userId.value = getAuthUseCase.getId() ?: 0
        }
    }

    fun login() {
        _isLogged.value = true
    }

    fun logout() {
        viewModelScope.launch {
            val user = User("", "", "", "", 0)
            getAuthUseCase.save(user)
            _isLogged.value = false
        }
    }

}