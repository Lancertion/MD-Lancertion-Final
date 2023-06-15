package com.app.lancertion.domain.use_case.diagnose_database

import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.DiagnoseDb
import com.app.lancertion.domain.repository.LancertionDiagnoseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiagnoseDatabaseUseCase @Inject constructor(
    private val repository: LancertionDiagnoseRepository
){

    suspend fun getDiagnoses(): List<DiagnoseDb> {
        return repository.getDiagnoses().first()
    }

    suspend fun addDiagnose(diagnose: DiagnoseDb) {
        repository.addDiagnose(diagnose)
    }

    suspend fun deleteDiagnose(diagnose: DiagnoseDb) {
        repository.deleteDiagnose(diagnose)
    }

}