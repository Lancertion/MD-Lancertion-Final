package com.app.lancertion.domain.repository

import com.app.lancertion.data.remote.dto.DiagnoseDto
import com.app.lancertion.data.remote.dto.Input
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.domain.model.DiagnoseDb
import kotlinx.coroutines.flow.Flow


interface LancertionDiagnoseRepository {

    suspend fun getDiagnose(body: DiagnoseBody): DiagnoseDto

    suspend fun getDiagnoses(): Flow<List<DiagnoseDb>>
//
    suspend fun getDiagnoseById(id: Int): DiagnoseDb?
//
    suspend fun addDiagnose(diagnose: DiagnoseDb)
//
    suspend fun deleteDiagnose(diagnose: DiagnoseDb)

}