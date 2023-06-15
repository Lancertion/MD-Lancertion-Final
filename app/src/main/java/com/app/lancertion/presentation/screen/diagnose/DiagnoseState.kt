package com.app.lancertion.presentation.screen.diagnose

import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.DiagnoseDb

data class DiagnoseState(
    val isLoading: Boolean = false,
    val diagnose: Diagnose? = null,
    val error: String = "",
    var diagnoses: List<DiagnoseDb> = emptyList()
)