package com.app.lancertion.presentation.screen.diagnose

import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.User

data class UserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)