package com.app.lancertion.data.repository

import com.app.lancertion.data.remote.LancertionDiagnoseApi
import com.app.lancertion.data.remote.dto.DiagnoseDto
import com.app.lancertion.data.remote.dto.Input
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.domain.repository.LancertionDiagnoseRepository
import javax.inject.Inject

class LancertionDiagnoseRepositoryImpl @Inject constructor(
    private val api: LancertionDiagnoseApi
): LancertionDiagnoseRepository {
    override suspend fun getDiagnose(body: DiagnoseBody): DiagnoseDto {
        return api.diagnose(body)
    }
}