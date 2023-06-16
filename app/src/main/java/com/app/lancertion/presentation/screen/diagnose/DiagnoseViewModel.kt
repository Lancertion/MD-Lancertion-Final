package com.app.lancertion.presentation.screen.diagnose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.domain.use_case.diagnose_database.DiagnoseDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnoseViewModel @Inject constructor(
    private val diagnoseDatabaseUseCase: DiagnoseDatabaseUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DiagnoseState())
    val state: StateFlow<DiagnoseState> = _state.asStateFlow()

    private val _dialogOpen = MutableStateFlow(false)
    val dialogOpen: StateFlow<Boolean> = _dialogOpen.asStateFlow()

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

    fun closeDialog() {
        _dialogOpen.value = false
    }

    fun openDialog() {
        _dialogOpen.value = true
    }
}