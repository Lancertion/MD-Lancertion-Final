package com.app.lancertion.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.lancertion.R
import com.app.lancertion.presentation.ui.theme.LancertionTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onDiagnose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = "Bagaimana kabar kamu hari ini?",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge,
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(start = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.doctor),
                contentDescription = "Doctor",
                modifier = Modifier.heightIn()
            )
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Demi kesehatanmu, ayo lakukan pemeriksaan sekarang juga!",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .alpha(0.7f)
                )
                Button(
                    onClick = onDiagnose
                ) {
                    Text(text = "PERIKSA")
                }
            }
        }
    }
}