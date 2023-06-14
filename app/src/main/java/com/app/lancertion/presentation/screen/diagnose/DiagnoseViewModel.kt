package com.app.lancertion.presentation.screen.diagnose

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.data.remote.request.RegisterBody
import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.repository.LancertionAuthRepository
import com.app.lancertion.domain.use_case.get_diagnose.GetDiagnoseUseCase
import com.app.lancertion.domain.use_case.auth_login.LoginUseCase
import com.app.lancertion.domain.use_case.auth_register.RegisterUseCase
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DiagnoseViewModel @Inject constructor(
    private val getDiagnoseUseCase: GetDiagnoseUseCase,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val repository: LancertionAuthRepository,
    private val getAuthUseCase: GetAuthUseCase
): ViewModel() {

    private var _listDiagnose = MutableStateFlow(ArrayList<Diagnose>())
    val listDiagnose: StateFlow<ArrayList<Diagnose>> = _listDiagnose

    private val _state = mutableStateOf<DiagnoseState>(DiagnoseState())
    val state: State<DiagnoseState> = _state

    private val _token = mutableStateOf<String>("")
    val token: State<String> = _token

    private val _user = mutableStateOf(UserState())
    val user: State<UserState> = _user

    fun addDiagnose(diagnose: Diagnose) {
        _listDiagnose.value.add(diagnose)
    }

    fun getDiagnose(body: DiagnoseBody) {
        getDiagnoseUseCase(body).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = DiagnoseState(diagnose = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    _state.value = DiagnoseState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = DiagnoseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun login() {
        val x = LoginBody("gcloud@gmail.com", "cloud123")
        loginUseCase(x).onEach {result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        repository.save(it)
                        getToken()
                    }
                }
                is Resource.Error -> {
                    Log.d("loginResult", "error")
                }
                is Resource.Loading -> {
                    Log.d("loginResult", "loading")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun create() {
        val x = RegisterBody(
            fullName = "duatiga",
            email = "email@123.com",
            password = "123123123123"
        )
        Log.d("registerin", x.toString())
        registerUseCase(x).launchIn(viewModelScope)
    }

    fun getToken() {
        viewModelScope.launch {
            _token.value = getAuthUseCase.getToken() ?: "gaada"
        }
    }

    fun logout() = runBlocking {
        repository.save(
            User("", "", "", "")
        )
        getToken()
    }
}