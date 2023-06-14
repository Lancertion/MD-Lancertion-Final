package com.app.lancertion.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.domain.model.User
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAuthUseCase: GetAuthUseCase
): ViewModel() {

    fun logout() {
        val user = User("", "", "", "")
        viewModelScope.launch {
            getAuthUseCase.save(user)
        }
    }

}