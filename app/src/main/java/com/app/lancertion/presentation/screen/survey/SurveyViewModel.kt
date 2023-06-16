package com.app.lancertion.presentation.screen.survey

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.common.util.Date
import com.app.lancertion.data.remote.dto.Input
import com.app.lancertion.data.remote.dto.asDiagnoseBody
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.DiagnoseDb
import com.app.lancertion.domain.use_case.diagnose_database.DiagnoseDatabaseUseCase
import com.app.lancertion.domain.use_case.get_diagnose.GetDiagnoseUseCase
import com.app.lancertion.presentation.screen.diagnose.DiagnoseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getDiagnoseUseCase: GetDiagnoseUseCase,
    private val diagnoseDatabaseUseCase: DiagnoseDatabaseUseCase
): ViewModel() {

    private val _diagnoseState = mutableStateOf<DiagnoseState>(DiagnoseState())
    val diagnoseState: State<DiagnoseState> = _diagnoseState

    private val _onDiagnose = mutableStateOf(false)
    val onDiagnose: State<Boolean> = _onDiagnose

    private var _questionIndex = MutableStateFlow(0)
    val questionIndex: StateFlow<Int> = _questionIndex

    private var _surveyAnswers = MutableStateFlow(mutableStateListOf(0, 0 , 0 , 0, 0 , 0, 0))
    val surveyAnswers: StateFlow<SnapshotStateList<Int>> = _surveyAnswers

    fun backPressed() {
        _questionIndex.value -= 1
    }

    fun nextPressed() {
        _questionIndex.value += 1
    }

    fun setSurveyAnswer(index: Int, answer: Int) {
        _surveyAnswers.value[index] = answer
    }

    fun diagnose() {
        val answers = surveyAnswers.value.toList()
        val input = Input(
            answers[0],
            answers[1],
            answers[2],
            answers[3],
            answers[4],
            answers[5],
            answers[6],
        )
        val diagnoseBody = input.asDiagnoseBody()
        getDiagnose(diagnoseBody)
    }

    fun getDiagnose(body: DiagnoseBody) {
        getDiagnoseUseCase(body).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { addToDatabase(it) }
                    Log.d("diagnose success", result.data.toString())
                    _diagnoseState.value = DiagnoseState(
                        diagnose = result.data,
                        isLoading = false
                    )
                    _onDiagnose.value = false
                }
                is Resource.Error -> {
                    if(result.message.toString() == "Couldn't reach server. Check your internet connection.") {
                        getDiagnose(body)
                    } else {
                        Log.d("diagnose error", result.message.toString())
                        _diagnoseState.value = DiagnoseState(
                            error = result.message ?: "An unexpected error occured",
                            isLoading = false
                        )
                        _onDiagnose.value = false
                    }
                }
                is Resource.Loading -> {
                    _onDiagnose.value = true

                    Log.d("diagnose loading", "please wait...")

                    _diagnoseState.value = DiagnoseState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addToDatabase(data: Diagnose) {
        val diagnose = data.input?.let {
            DiagnoseDb(
                date = Date().getNow(),
                alcoholUse = it.alcoholUse,
                balacedDiet = it.balacedDiet,
                coughingOfBloodval = it.coughingOfBloodval,
                dustAllergy = it.dustAllergy,
                geneticRisk = it.geneticRisk,
                obesity = it.obesity,
                smoker = it.smoker,
                result = data.result
            )
        }
        viewModelScope.launch {
            diagnose?.let { diagnoseDatabaseUseCase.addDiagnose(it) }
        }
    }

    fun resetDiagnose() {
        _diagnoseState.value = diagnoseState.value.copy(diagnose = null)
        _surveyAnswers.value = mutableStateListOf(0, 0 , 0 , 0, 0 , 0, 0)
        _questionIndex.value = 0
    }

}