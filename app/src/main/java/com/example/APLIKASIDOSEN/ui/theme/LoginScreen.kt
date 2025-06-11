package com.example.APLIKASIDOSEN.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.APLIKASIDOSEN.data.LoginViewModel
import com.example.APLIKASIDOSEN.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit
) {
    val loginState by viewModel.loginState.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFF121212)
    val surfaceColor = Color(0xFF1C1C1C)
    val accentColor = Color(0xFF27AE60)
    val textColor = Color.White
    val hintColor = Color.LightGray

    LaunchedEffect(loginState) {
        if (loginState) {
            val token = viewModel.getAccessToken()
            token?.let {
                onLoginSuccess(it)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Background Image (opsional)
        Image(
            painter = painterResource(id = R.drawable.bg_pattren),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.1f // supaya tidak mengganggu elemen foreground
        )

        // Login Card
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .border(
                    width = 2.dp,
                    color = accentColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(surfaceColor, RoundedCornerShape(20.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val logo: Painter = painterResource(id = R.drawable.logo_uin)
            Image(painter = logo, contentDescription = "Logo", modifier = Modifier.size(80.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "السَّلَامُ عَلَيْكُمْ",
                color = textColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Silakan masuk di sini.",
                color = hintColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = hintColor) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = accentColor,
                    unfocusedLabelColor = hintColor,
                    cursorColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = hintColor) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = textColor)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = accentColor,
                    unfocusedLabelColor = hintColor,
                    cursorColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text("Login", color = textColor)
            }
        }
    }
}
