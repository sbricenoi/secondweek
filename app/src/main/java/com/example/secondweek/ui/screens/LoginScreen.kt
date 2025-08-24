package com.example.secondweek.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.secondweek.data.UserRepository

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var remember by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun onLogin(): Boolean {
        val success = UserRepository.authenticate(email.trim(), password)
        errorMessage = if (success) null else "Correo o contraseña incorrectos"
        return success
    }
}

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Acceso",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Campo de email" }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Campo de contraseña" }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.remember,
                onCheckedChange = { viewModel.remember = it },
                modifier = Modifier.semantics { contentDescription = "Recordarme" }
            )
            Text(text = "Recordarme")
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .semantics { contentDescription = "Olvidé mi contraseña" }
                    .clickable { onNavigateToForgot() }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Acceder")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("Crear cuenta")
        }
        viewModel.errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}


