package com.app.lancertion.data.repository

import android.util.Log
import com.app.lancertion.data.data_source.DiagnoseDao
import com.app.lancertion.data.remote.LancertionDiagnoseApi
import com.app.lancertion.data.remote.dto.DiagnoseDto
import com.app.lancertion.data.remote.dto.Input
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.domain.model.DiagnoseDb
import com.app.lancertion.domain.repository.LancertionDiagnoseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LancertionDiagnoseRepositoryImpl @Inject constructor(
    private val api: LancertionDiagnoseApi,
    private val dao: DiagnoseDao
): LancertionDiagnoseRepository {
    override suspend fun getDiagnose(body: DiagnoseBody): DiagnoseDto {
        return api.diagnose(body)
    }
//
    override suspend fun getDiagnoses(): Flow<List<DiagnoseDb>> {
        return dao.getDiagnoses()
    }
//
    override suspend fun getDiagnoseById(id: Int): DiagnoseDb? {
        return dao.getDiagnose(id)
    }
//
    override suspend fun addDiagnose(diagnose: DiagnoseDb) {
        Log.d("insert diagnose", diagnose.toString())
        dao.addDiagnose(diagnose)
    }
//
    override suspend fun deleteDiagnose(diagnose: DiagnoseDb) {
        dao.deleteDiagnose(diagnose)
    }
}