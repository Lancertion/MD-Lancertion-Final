package com.app.lancertion.presentation.screen.login

import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.Login
import com.app.lancertion.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    var data: User? = null,
    val error: String = ""
)
