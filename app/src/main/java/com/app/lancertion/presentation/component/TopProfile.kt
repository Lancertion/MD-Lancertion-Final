package com.app.lancertion.presentation.component

import android.util.Log.e
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lancertion.R
import com.app.lancertion.common.util.Date
import com.app.lancertion.presentation.ui.theme.LancertionTheme

@Composable
fun TopProfile(
    name: String,
    onLogout: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Row {
            Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "Avatar")
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = Date().getGreeting(),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp)
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(top = 4.dp))
            }
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Keluar")
            }
        }
    }
}

@Preview
@Composable
fun TopProfilePreview() {
    LancertionTheme {
        TopProfile("", {})
    }
}