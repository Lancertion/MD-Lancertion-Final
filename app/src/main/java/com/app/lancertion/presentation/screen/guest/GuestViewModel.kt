package com.app.lancertion.presentation.screen.guest

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
class GuestViewModel @Inject constructor(
    private val getAuthUseCase: GetAuthUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(GuestUiState())
    val uiState: StateFlow<GuestUiState> = _uiState.asStateFlow()

    private val _dialogOpen = MutableStateFlow(false)
    val dialogOpen: StateFlow<Boolean> = _dialogOpen.asStateFlow()

    fun setName(name: String) {
        _uiState.value = uiState.value.copy(name = name)
    }

    fun login() {
        viewModelScope.launch {
            getAuthUseCase.save(User(
                fullName = uiState.value.name,
                token = uiState.value.token,
                email = "",
                password = "",
                id = 0
            ))

            openDialog()
        }
    }

    fun closeDialog() {
        _uiState.value = uiState.value.copy("")
        _dialogOpen.value = false
    }

    fun openDialog() {
        _dialogOpen.value = true
    }

}

