package com.app.lancertion.domain.repository

import com.app.lancertion.data.remote.dto.DiagnoseDto
import com.app.lancertion.data.remote.dto.Input
import com.app.lancertion.data.remote.request.DiagnoseBody

interface LancertionDiagnoseRepository {

    suspend fun getDiagnose(body: DiagnoseBody): DiagnoseDto

}