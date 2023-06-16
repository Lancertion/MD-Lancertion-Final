package com.app.lancertion.presentation.screen.diagnose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Details
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lancertion.R
import com.app.lancertion.domain.model.DiagnoseDb
import com.app.lancertion.presentation.ui.theme.Danger
import com.app.lancertion.presentation.ui.theme.LancertionTheme
import com.app.lancertion.presentation.ui.theme.Warning

@Composable
fun DiagnoseScreen(
    viewModel: DiagnoseViewModel
) {
    viewModel.getDiagnoses()
    val listDiagnose by viewModel.state.collectAsState()
    var isCleared by remember { mutableStateOf(false) }
    val dialogOpen by viewModel.dialogOpen.collectAsState()

    if(isCleared || listDiagnose.diagnoses.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.health_1),
                contentDescription = null
            )
            Text(
                text = "Kamu belum memiliki histori apapun. Ayo lakukan pemeriksaan agar kamu bisa mengetahui seberapa sehat tubuhmu!",
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 8.dp)
        ) {
            Result(listDiagnose.diagnoses.last(), modifier = Modifier.padding(horizontal = 16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Riwayat",
                    modifier = Modifier
                        .alpha(0.7f)
                        .weight(1f)
                )
                Button(onClick = {
                    viewModel.openDialog()
                }) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = null
                    )
                }
            }
            History(listDiagnose.diagnoses.reversed())
        }
    }

    if(dialogOpen) {
        Alert(
            removeAllData = {
                viewModel.deleteAll()
                isCleared = true
            },
            closeDialog = {
                viewModel.closeDialog()
            }
        )
    }
}

@Composable
fun Result(
    lastDiagnose: DiagnoseDb,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = when (lastDiagnose.result) {
                    "Medium" -> Danger
                    "High" -> Warning
                    else -> MaterialTheme.colorScheme.primary
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = lastDiagnose.result,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = when(lastDiagnose.result) {
                "Medium" -> "Kamu masih dalam batas aman. Tetap jaga kesehatanmu, ya!"
                "High" -> "Kamu berpotensi memiliki kanker paru! Segera konsultasikan ke rumah sakit!"
                else -> "Tetap jaga kesehatanmu!"
            },
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun History(
    listDiagnose: List<DiagnoseDb>
) {
    val dataDetail = remember {
        mutableStateOf(listDiagnose.first())
    }

    val showDetail = remember {
        mutableStateOf(false)
    }

    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
    ) {
        items(listDiagnose) {
            it.apply {
                HistoryCard(
                    date = date,
                    result = result,
                    onClickDetail = {
                        dataDetail.value = it
                        showDetail.value = true
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }

    if(showDetail.value) {
        AlertDetail(dataDetail = dataDetail.value) {
            showDetail.value = false
        }
    }
}

@Composable
fun AlertDetail(
    dataDetail: DiagnoseDb,
    closeDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            closeDialog()
        },
        icon = { Icon(Icons.Filled.Details, contentDescription = null) },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
            }) {
                Text(text = "Okay")
            }
        },
        text = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
                ) {
                    Text(text = dataDetail.result, fontWeight = FontWeight.Medium)
                    Text(text = dataDetail.date)
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val alcohol = listOf(
                        "Tidak mengonsumsi alkohol",
                        "Jarang sekali",
                        "Sekali dalam sebulan",
                        "Beberapa kali dalam sebulan",
                        "Sekali dalam seminggu",
                        "Beberapa kali dalam seminggu",
                        "Sering sekali",
                        "Setiap hari"
                    )
                    Text(text = "Alkohol", fontWeight = FontWeight.Medium)
                    Text(text = alcohol[dataDetail.alcoholUse - 1])
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val protein = listOf(
                        "Setiap hari",
                        "Beberapa kali dalam seminggu",
                        "Sekali dalam seminggu",
                        "Beberapa kali dalam sebulan",
                        "Jarang ",
                        "Jarang sekali",
                        "Tidak pernah",
                    )
                    Text(text = "Makanan tinggi protein", fontWeight = FontWeight.Medium)
                    Text(text = protein[dataDetail.balacedDiet - 1])
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val blood = listOf(
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
                    Text(text = "Batuk darah", fontWeight = FontWeight.Medium)
                    Text(text = blood[dataDetail.coughingOfBloodval - 1])
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val dust = listOf(
                        "Tidak pernah",
                        "Jarang sekali",
                        "Sekali dalam sebulan",
                        "Beberapa kali dalam sebulan",
                        "Sekali dalam seminggu",
                        "Beberapa kali dalam seminggu",
                        "Sering sekali",
                        "Setiap hari",
                    )
                    Text(text = "Alergi debu", fontWeight = FontWeight.Medium)
                    Text(text = dust[dataDetail.dustAllergy - 1])
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val genetik = listOf(
                        "Sangat rendah",
                        "Agak rendah",
                        "Rendah",
                        "Sedang",
                        "Agak tinggi",
                        "Tinggi",
                        "Sangat tinggi",
                    )
                    Text(text = "Resiko genetik", fontWeight = FontWeight.Medium)
                    Text(text = genetik[dataDetail.geneticRisk - 1])
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val obesity = listOf(
                        "Kurang dari 50 kg",
                        "50-59 kg",
                        "60-69 kg",
                        "70-79 kg",
                        "80-89 kg",
                        "90-99 kg",
                        "100 kg atau lebih",
                    )
                    Text(text = "Berat badan", fontWeight = FontWeight.Medium)
                    Text(text = obesity[dataDetail.obesity - 1])
                }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    val smoke = listOf(
                        "Kurang dari 5 menit",
                        "5-15 menit",
                        "15-25 menit",
                        "25-35 menit",
                        "35-45 menit",
                        "45-55 menit",
                        "55 menit-1 jam",
                        "lebih dari 1 jam",
                    )
                    Text(text = "Paparan asap rokok", fontWeight = FontWeight.Medium)
                    Text(text = smoke[dataDetail.smoker - 1])
                }
            }
        }
    )

}


@Composable
fun HistoryCard(
    date: String,
    result: String,
    onClickDetail: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = Color(0xFFF6F6F6),
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = date,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(onClick = {
                onClickDetail()
            }) {
                Text(text = "Detail")
            }
        }
        Text(
            text = result,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.alpha(0.7f)
        )
    }
}

@Preview
@Composable
fun HistoryCardPreview() {
    LancertionTheme {
        HistoryCard(date = "Senin, 29 Mei 2023", result = "Medium", {} , Modifier)
    }
}

@Composable
fun Alert(
    closeDialog: () -> Unit,
    removeAllData: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            closeDialog()
        },
        icon = { Icon(Icons.Filled.DeleteSweep, contentDescription = null) },
        confirmButton = {
            TextButton(onClick = {
                removeAllData()
                closeDialog()
            }) {
                Text(text = "Iya")
            }
        },
        text = {
            Text(text = "Apakah anda yakin akan menghapus semua histori diagnosa?")
        }
    )

}