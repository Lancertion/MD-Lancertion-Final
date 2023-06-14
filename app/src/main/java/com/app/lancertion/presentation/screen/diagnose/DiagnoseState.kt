package com.app.lancertion.presentation.screen.diagnose

import com.app.lancertion.domain.model.Diagnose

data class DiagnoseState(
    val isLoading: Boolean = false,
    val diagnose: Diagnose? = null,
    val error: String = ""
)