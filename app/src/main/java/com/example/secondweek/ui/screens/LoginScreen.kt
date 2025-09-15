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

/**
 * ViewModel para la pantalla de inicio de sesión
 * Gestiona el estado del formulario de login y la autenticación
 */
class LoginViewModel : ViewModel() {
    // Estado del formulario
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var remember by mutableStateOf(false)
        private set
    
    // Estado de la UI
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var successMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    // Funciones para actualizar el estado
    fun updateEmail(value: String) { 
        email = value
        clearMessages()
    }
    
    fun updatePassword(value: String) { 
        password = value
        clearMessages()
    }
    
    fun updateRemember(value: Boolean) { remember = value }

    /**
     * Limpia los mensajes de estado
     */
    private fun clearMessages() {
        errorMessage = null
        successMessage = null
    }

    /**
     * Valida si un email tiene formato correcto
     */
    private fun isValidEmail(text: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(text.trim()).matches()

    /**
     * Procesa el inicio de sesión
     * Valida las credenciales y autentica al usuario
     */
    fun onLogin() {
        clearMessages()
        isLoading = true

        try {
            // Validaciones básicas
            when {
                email.isBlank() -> {
                    errorMessage = "El correo electrónico es requerido"
                    return
                }
                !isValidEmail(email) -> {
                    errorMessage = "Formato de correo electrónico inválido"
                    return
                }
                password.isBlank() -> {
                    errorMessage = "La contraseña es requerida"
                    return
                }
                password.length < 6 -> {
                    errorMessage = "La contraseña debe tener al menos 6 caracteres"
                    return
                }
            }

            // Intentar autenticar
            val success = UserRepository.authenticate(email.trim(), password)
            if (success) {
                val user = UserRepository.findUserByEmail(email.trim())
                successMessage = "¡Bienvenido/a ${user?.name ?: ""}! Inicio de sesión exitoso"
            } else {
                errorMessage = "Credenciales incorrectas. Verifica tu correo y contraseña"
            }
        } catch (e: Exception) {
            errorMessage = "Error inesperado: ${e.message}"
        } finally {
            isLoading = false
        }
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
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { 
                contentDescription = "Pantalla de inicio de sesión"
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Correo electrónico") },
            singleLine = true,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Campo de correo electrónico para iniciar sesión"
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = viewModel::updatePassword,
            label = { Text("Contraseña") },
            singleLine = true,
            enabled = !viewModel.isLoading,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Campo de contraseña para iniciar sesión"
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.remember,
                onCheckedChange = viewModel::updateRemember,
                enabled = !viewModel.isLoading,
                modifier = Modifier
                    .size(48.dp) // Tamaño táctil mínimo
                    .semantics { 
                        contentDescription = "Recordar mis credenciales en este dispositivo"
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Recordarme",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .semantics { 
                        contentDescription = "Enlace para recuperar contraseña olvidada"
                    }
                    .clickable(enabled = !viewModel.isLoading) { 
                        onNavigateToForgot() 
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = viewModel::onLogin,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Altura mínima táctil
                .semantics { 
                    contentDescription = "Botón iniciar sesión"
                }
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = onNavigateToRegister,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .height(48.dp)
                .semantics { 
                    contentDescription = "Crear nueva cuenta de usuario"
                }
        ) {
            Text(
                text = "Crear cuenta nueva",
                style = MaterialTheme.typography.labelLarge
            )
        }
        // Mensajes de estado
        viewModel.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .semantics { 
                            contentDescription = "Error de inicio de sesión: $message"
                        }
                )
            }
        }
        
        viewModel.successMessage?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .semantics { 
                            contentDescription = "Éxito en inicio de sesión: $message"
                        }
                )
            }
        }
    }
}


