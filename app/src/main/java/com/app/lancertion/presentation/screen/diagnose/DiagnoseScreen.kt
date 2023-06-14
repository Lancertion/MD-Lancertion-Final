package com.app.lancertion.presentation.screen.diagnose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.lancertion.R
import com.app.lancertion.data.remote.dto.Input
import com.app.lancertion.data.remote.request.DiagnoseBody
import com.app.lancertion.data.remote.request.LoginBody
import com.app.lancertion.domain.model.Diagnose
import com.app.lancertion.domain.model.Login
import com.app.lancertion.presentation.ui.theme.Danger
import com.app.lancertion.presentation.ui.theme.LancertionTheme
import com.app.lancertion.presentation.ui.theme.Warning

@Composable
fun DiagnoseScreen(
    viewModel: DiagnoseViewModel
) {
    val listDiagnose by viewModel.listDiagnose.collectAsState()


    viewModel.addDiagnose(
        Diagnose(
            result = "Medium",
            date = "Senin, 22 Mei 2023"
        )
    )
    viewModel.addDiagnose(
        Diagnose(
            result = "Low",
            date = "Senin, 29 Mei 2023"
        )
    )


    viewModel.addDiagnose(
        Diagnose(
            result = "Medium",
            date = "Senin, 29 Mei 2023"
        )
    )

    val lastDiagnose = listDiagnose.last()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 8.dp)
    ) {
        Result(lastDiagnose, modifier = Modifier.padding(horizontal = 16.dp))
        Text(
            text = "Riwayat",
            modifier = Modifier
                .alpha(0.7f)
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )
        History(listDiagnose.reversed() as ArrayList<Diagnose>)
    }


}

@Composable
fun Result(
    lastDiagnose: Diagnose,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = when(lastDiagnose.result) {
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
            text = "Kamu masih dalam batas aman. Tetap jaga kesehatanmu, ya!",
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun History(
    listDiagnose: ArrayList<Diagnose>
) {
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
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun HistoryCard(
    date: String,
    result: String,
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
            Button(onClick = { /*TODO*/ }) {
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
        HistoryCard(date = "Senin, 29 Mei 2023", result = "Medium", Modifier)
    }
}