package com.app.lancertion.data.remote.dto

import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.domain.model.Diagnose

data class DiagnoseDto(
    val error: Boolean,
    val input: DiagnoseBody,
    val result: String
)

fun DiagnoseDto.toDiagnose(): Diagnose {
    return Diagnose(
        error = error,
        input = input,
        result = result
    )
}