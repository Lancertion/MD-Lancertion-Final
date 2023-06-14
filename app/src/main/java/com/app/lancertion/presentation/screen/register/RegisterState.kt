package com.app.lancertion.presentation.screen.register

import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.Register


data class RegisterState(
    val isLoading: Boolean = false,
    val data: Register? = null,
    val error: String = ""
)