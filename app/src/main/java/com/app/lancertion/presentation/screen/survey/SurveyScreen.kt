package com.app.lancertion.presentation.screen.survey

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lancertion.domain.model.Survey
import com.app.lancertion.presentation.ui.theme.LancertionTheme
import java.time.LocalTime
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyScreen(
    viewModel: SurveyViewModel,
    navigateBack: () -> Unit,
    navigateToResult: () -> Unit
) {
    val questionIndex by viewModel.questionIndex.collectAsState()
    val result by viewModel.surveyAnswers.collectAsState()

    BackHandler(true) {
        if(questionIndex > 0) {
            viewModel.backPressed()
        } else {
            navigateBack()
        }
    }

    Scaffold(
        topBar = {
            SurveyTop(
                questionIndex = questionIndex,
                onCancelClicked = navigateBack
            )
        },
        bottomBar = {
            SurveyBottom(
                questionIndex = questionIndex,
                onNextClicked = {
                    viewModel.nextPressed()
                },
                onPrevClicked = {
                    viewModel.backPressed()
                },
                onFinishClicked = {
                    viewModel.diagnose()
                },
                isNextDisabled = result[questionIndex] == 0
            )
        }
    ) {
        when(viewModel.onDiagnose.value) {
            true -> {
                Text(
                    text = "Please wait...",
                    modifier = Modifier.padding(it)
                )
            }
            false -> {
                StartSurvey(
                    index = questionIndex,
                    result = result,
                    setAnswer = { index, answer ->
                        viewModel.setSurveyAnswer(index, answer)
                    },
                    modifier = Modifier.padding(it))

                if(viewModel.diagnoseState.value.diagnose != null) {
                    navigateToResult()
                    viewModel.resetDiagnose()
                }
            }
        }
        
    }

    
}

@Composable
fun StartSurvey(
    index: Int,
    result: SnapshotStateList<Int>,
    setAnswer: (Int, Int) -> Unit,
    modifier: Modifier
) {
    // DUMMY SURVEY
    val rand = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Random(LocalTime.now().nano)
    } else {
        Random(0)
    }
    val listSurvey = arrayListOf<Survey>()
    for(i in 1 ..7) {
        val randomOption = rand.nextInt(3, 9)
        val option = arrayListOf<String>()
        for(j in 1 .. randomOption) {
            option.add("Option $j")
        }
        listSurvey.add(
            Survey(
                question = "Question $i",
                option = option
            )
        )
    }
    // END DUMMY SURVEY

    when(index) {
        in 0 until 7 -> {
            Column(
                modifier = modifier
            ) {
                Text(
                    text = listSurvey[index].question,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listSurvey[index].option) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = result[index] == listSurvey[index].option.indexOf(it) + 1,
                                onClick = {
                                    setAnswer(index, listSurvey[index].option.indexOf(it) + 1)
                                }
                            )
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SurveyTop(
    questionIndex: Int,
    onCancelClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "${questionIndex + 1} dari 7")
        Button(onClick = onCancelClicked) {
            Text(text = "Batal")
        }
    }
}

@Composable
fun SurveyBottom(
    questionIndex: Int,
    isNextDisabled: Boolean,
    onNextClicked: () -> Unit,
    onPrevClicked: () -> Unit,
    onFinishClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = if(questionIndex > 0) Arrangement.SpaceBetween else Arrangement.End,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if(questionIndex > 0) {
            Button(onClick = onPrevClicked) {
                Text(text = "Sebelumnya")
            }
        }
        if(questionIndex + 1 < 7) {
            Button(
                enabled = !isNextDisabled,
                onClick = onNextClicked
            ) {
                Text(text = "Selanjutnya")
            }
        } else {
            Button(
                enabled = !isNextDisabled,
                onClick = onFinishClicked
            ) {
                Text(text = "Selesai")
            }
        }

    }
}

@Preview
@Composable
fun SurveyScreenPreview() {
    LancertionTheme {
//        SurveyScreen() {}
    }
}