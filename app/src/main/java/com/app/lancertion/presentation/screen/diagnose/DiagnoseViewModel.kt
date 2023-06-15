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
import com.app.lancertion.domain.model.DiagnoseDb
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.repository.LancertionAuthRepository
import com.app.lancertion.domain.use_case.get_diagnose.GetDiagnoseUseCase
import com.app.lancertion.domain.use_case.auth_login.LoginUseCase
import com.app.lancertion.domain.use_case.auth_register.RegisterUseCase
import com.app.lancertion.domain.use_case.diagnose_database.DiagnoseDatabaseUseCase
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DiagnoseViewModel @Inject constructor(
    private val diagnoseDatabaseUseCase: DiagnoseDatabaseUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DiagnoseState())
    val state: StateFlow<DiagnoseState> = _state.asStateFlow()

    init {
        getDiagnoses()
    }

    fun getDiagnoses() {
        viewModelScope.launch {
            _state.value.diagnoses = diagnoseDatabaseUseCase.getDiagnoses()
        }
    }

    fun deleteAll() {
        val diagnoses = state.value.diagnoses
        viewModelScope.launch {
            diagnoses.forEach {
                diagnoseDatabaseUseCase.deleteDiagnose(it)
            }
        }
        _state.value.diagnoses = emptyList()
    }
}