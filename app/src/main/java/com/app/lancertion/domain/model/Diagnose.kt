package com.app.lancertion.domain.model

import com.app.lancertion.data.remote.request.DiagnoseBody

data class Diagnose(
    val error: Boolean = false,
    val input: DiagnoseBody? = null,
    val result: String,
    val date: String = ""
)
