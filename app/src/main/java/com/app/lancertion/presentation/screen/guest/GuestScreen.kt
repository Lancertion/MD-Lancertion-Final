package com.app.lancertion.presentation.screen.guest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestScreen(
    viewModel: GuestViewModel,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogOpen by viewModel.dialogOpen.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Masuk sebagai Tamu",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = uiState.name,
            onValueChange = { viewModel.setName(it) },
            label = { Text(text = "Nama")},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "password"
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .width(150.dp)
                    .padding(start = 8.dp)
            ) {
                Text(text = "Masuk")
            }
        }
    }

    if(dialogOpen) {
        Alert(
            guestName = uiState.name,
            closeDialog = { viewModel.closeDialog() },
            navigateToHome = navigateToHome
        )
    }
}

@Composable
fun Alert(
    guestName: String,
    closeDialog: () -> Unit,
    navigateToHome: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            closeDialog()
            navigateToHome()
        },
        icon = { Icon(Icons.Filled.Warning, contentDescription = null) },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
                navigateToHome()
            }) {
                Text(text = "Iya")
            }
        },
        text = {
            Text(text = "Kami menyarankan Anda untuk membuat akun terlebih dahulu, apakah Anda yakin akan memulai sebagai $guestName?")
        }
    )

}