package com.example.secondweek.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import com.example.secondweek.data.User
import com.example.secondweek.data.UserRepository
import com.example.secondweek.ui.components.UsersGrid
import com.example.secondweek.ui.components.UsersTable

class RegisterViewModel : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirm by mutableStateOf("")
    var termsAccepted by mutableStateOf(false)
    var preference by mutableStateOf("Alta")
    var gender by mutableStateOf("Prefiero no decir")
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    private fun isValidEmail(text: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()

    fun onRegister() {
        successMessage = null
        errorMessage = null
        if (name.isBlank()) {
            errorMessage = "Nombre requerido"; return
        }
        if (!isValidEmail(email)) {
            errorMessage = "Email inválido"; return
        }
        if (password.length < 6) {
            errorMessage = "Contraseña mínima de 6 caracteres"; return
        }
        if (password != confirm) {
            errorMessage = "Las contraseñas no coinciden"; return
        }
        if (!termsAccepted) {
            errorMessage = "Debes aceptar los términos"; return
        }
        val result = UserRepository.addUser(
            User(
                name = name.trim(),
                email = email.trim(),
                password = password,
                preference = preference,
                acceptTerms = termsAccepted,
                gender = gender
            )
        )
        result.onSuccess { successMessage = "Usuario registrado" }
            .onFailure { errorMessage = it.message }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onBack: () -> Unit, viewModel: RegisterViewModel = viewModel()) {
    val preferences = listOf("Alta", "Media", "Baja")
    val genders = listOf("Femenino", "Masculino", "Prefiero no decir")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Registro", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.confirm,
            onValueChange = { viewModel.confirm = it },
            label = { Text("Confirmar contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // ComboBox (ExposedDropdownMenuBox)
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = viewModel.preference,
                onValueChange = {},
                readOnly = true,
                label = { Text("Preferencia") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                preferences.forEach { option ->
                    DropdownMenuItem(text = { Text(option) }, onClick = {
                        viewModel.preference = option
                        expanded = false
                    })
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        // Radio buttons
        Text("Género")
        genders.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = viewModel.gender == option,
                        onClick = { viewModel.gender = option }
                    )
            ) {
                RadioButton(
                    selected = viewModel.gender == option,
                    onClick = { viewModel.gender = option }
                )
                Text(option)
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewModel.termsAccepted,
                onCheckedChange = { viewModel.termsAccepted = it },
                modifier = Modifier.semantics { contentDescription = "Aceptar términos" }
            )
            Text("Acepto los términos y condiciones")
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onRegister() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) { Text("Registrarse") }

        viewModel.errorMessage?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        viewModel.successMessage?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onBack) { Text("Volver") }

        Spacer(Modifier.height(24.dp))
        Text("Tabla de usuarios registrados")
        UsersTable(users = UserRepository.getAllUsers())

        Spacer(Modifier.height(24.dp))
        Text("Grilla de usuarios registrados")
        UsersGrid(users = UserRepository.getAllUsers())
    }
}


