package com.app.lancertion.presentation.screen.survey

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lancertion.R
import com.app.lancertion.domain.model.Survey
import com.app.lancertion.presentation.ui.theme.LancertionTheme
import com.app.lancertion.presentation.ui.theme.Primary

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

    when(viewModel.onDiagnose.value) {
        true -> {
            LoadingSurvey(modifier = Modifier)
        }
        false -> {
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
fun LoadingSurvey(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(Primary)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lancertion_logo),
                    contentDescription = "Lancertion Logo",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .height(72.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Lancertion",
                    fontSize = 28.sp,
                    color = Color.White,
                )
            }
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPrev() {
    LancertionTheme {
        LoadingSurvey()
    }
}

@Composable
fun StartSurvey(
    index: Int,
    result: SnapshotStateList<Int>,
    setAnswer: (Int, Int) -> Unit,
    modifier: Modifier
) {
    val listSurvey = listOf(
        // ALCOHOL
        Survey(
            question = "Berapa frekuensi konsumsi alkohol Anda?",
            option = listOf(
                "Tidak mengonsumsi alkohol",
                "Jarang sekali",
                "Sekali dalam sebulan",
                "Beberapa kali dalam sebulan",
                "Sekali dalam seminggu",
                "Beberapa kali dalam seminggu",
                "Sering sekali",
                "Setiap hari"
            )
        ),
        // DIET
        Survey(
            question = "Seberapa sering Anda mengonsumsi makanan tinggi serat dan makanan tinggi protein?",
            option = listOf(
                "Setiap hari",
                "Beberapa kali dalam seminggu",
                "Sekali dalam seminggu",
                "Beberapa kali dalam sebulan",
                "Jarang ",
                "Jarang sekali",
                "Tidak pernah",
            )
        ),
        // BLOOD
        Survey(
            question = "Seberapa sering batuk darah yang pernah Anda alami?",
            option = listOf(
                "Tidak pernah",
                "Jarang sekali",
                "Sekali dalam sebulan",
                "Beberapa kali dalam sebulan",
                "Sekali dalam seminggu",
                "Beberapa kali dalam seminggu",
                "Sering sekali",
                "Hampir setiap hari",
                "Setiap hari",
            )
        ),
        // DEBU
        Survey(
            question = "Seberapa sering gejala alergi debu yang biasanya Anda alami?",
            option = listOf(
                "Tidak pernah",
                "Jarang sekali",
                "Sekali dalam sebulan",
                "Beberapa kali dalam sebulan",
                "Sekali dalam seminggu",
                "Beberapa kali dalam seminggu",
                "Sering sekali",
                "Setiap hari",

                )
        ),
        // GENETIK
        Survey(
            question = "Seberapa tinggi tingkat risiko genetik yang Anda anggap untuk mengidentifikasi terkena kanker paru?",
            option = listOf(
                "Sangat rendah",
                "Agak rendah",
                "Rendah",
                "Sedang",
                "Agak tinggi",
                "Tinggi",
                "Sangat tinggi",
            )
        ),
        // OBES
        Survey(
            question = "Berapa berat badan Anda (dalam kg)?",
            option = listOf(
                "Kurang dari 50 kg",
                "50-59 kg",
                "60-69 kg",
                "70-79 kg",
                "80-89 kg",
                "90-99 kg",
                "100 kg atau lebih",
            )
        ),
        // SMOKER
        Survey(
            question = "Seberapa lama Anda biasanya terpapar asap rokok setiap kali terjadi?",
            option = listOf(
                "Kurang dari 5 menit",
                "5-15 menit",
                "15-25 menit",
                "25-35 menit",
                "35-45 menit",
                "45-55 menit",
                "55 menit-1 jam",
                "lebih dari 1 jam",
            )
        ),






    )
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp)
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