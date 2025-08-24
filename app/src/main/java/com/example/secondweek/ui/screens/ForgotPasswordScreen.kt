package com.example.secondweek.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = "Recuperar contraseña", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                message = if (valid) "Se envió un enlace a tu correo" else "Email inválido"
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) { Text("Enviar enlace") }

        message?.let {
            Spacer(Modifier.height(12.dp))
            Text(it)
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onBack) { Text("Volver") }
    }
}


