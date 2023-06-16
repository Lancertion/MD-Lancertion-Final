package com.app.lancertion.domain.use_case.get_diagnose

import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.toDiagnose
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.repository.LancertionDiagnoseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDiagnoseUseCase @Inject constructor(
    private val repository: LancertionDiagnoseRepository
) {
    operator fun invoke(body: DiagnoseBody): Flow<Resource<Diagnose>> = flow {
        try {
            emit(Resource.Loading())
            val diagnose = repository.getDiagnose(body).toDiagnose()

            emit(Resource.Success(diagnose))
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}