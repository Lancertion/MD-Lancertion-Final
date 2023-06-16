package com.app.lancertion.presentation.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lancertion.R
import com.app.lancertion.presentation.ui.theme.LancertionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginState = viewModel.loginState.value
    var showPassword by remember { mutableStateOf(value = false) }
    val isError by viewModel.isError.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
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

        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Masuk",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = uiState.email,
                onValueChange = { viewModel.setEmail(it) },
                label = { Text(text = "Email")},
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "password"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            TextField(
                value = uiState.password,
                onValueChange = { viewModel.setPassword(it) },
                label = { Text(text = "Kata sandi")},
                singleLine = true,
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = "password"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Masuk")
                }
                Text(text = "atau")
                Row {
                    Button(
                        onClick = navigateToGuest,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .width(150.dp)
                            .padding(end = 8.dp)
                    ) {
                        Text(text = "Tamu")
                    }
                    Button(
                        onClick = navigateToRegister,
                        modifier = Modifier
                            .width(150.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(text = "Daftar")
                    }
                }
            }
        }
    }

    if(!loginState.isLoading) {
        if(loginState.data != null) {
            onLogin()
            loginState.data = null
        }
    }

//    if(loginState.isLoading) {
//
//    } else {
//        if(loginState.data != null) {
//
//
//        } else {
//
//        }
//    }

    if(isError) {
        Alert {
            viewModel.unsetError()
        }
    }
}

@Composable
fun Alert(
    closeDialog: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            closeDialog()
        },
        icon = { Icon(Icons.Filled.Error, contentDescription = null) },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
            }) {
                Text(text = "Iya")
            }
        },
        text = {
            Text(text = "Gagal masuk, pastikan email dan password Anda sudah benar")
        }
    )
}

@Preview
@Composable
fun Preview() {
    LancertionTheme {
//        LoginScreen(onLogin = { /*TODO*/ })
    }
}