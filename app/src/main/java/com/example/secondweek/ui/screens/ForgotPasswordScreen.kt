package com.example.secondweek.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.secondweek.data.UserRepository

/**
 * ViewModel para la pantalla de recuperación de contraseña
 * Gestiona el estado del formulario y la lógica de recuperación
 */
class ForgotPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set
    var message by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var isSuccess by mutableStateOf(false)
        private set

    fun updateEmail(value: String) {
        email = value
        message = null
        isSuccess = false
    }

    /**
     * Valida si un email tiene formato correcto
     */
    private fun isValidEmail(text: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(text.trim()).matches()

    /**
     * Procesa la solicitud de recuperación de contraseña
     */
    fun onSendRecoveryLink() {
        isLoading = true
        message = null
        isSuccess = false

        try {
            when {
                email.isBlank() -> {
                    message = "El correo electrónico es requerido"
                    return
                }
                !isValidEmail(email) -> {
                    message = "Formato de correo electrónico inválido"
                    return
                }
            }

            // Verificar si el usuario existe
            val userExists = UserRepository.findUserByEmail(email.trim()) != null
            
            if (userExists) {
                message = "Se ha enviado un enlace de recuperación a ${email.trim()}"
                isSuccess = true
            } else {
                message = "No se encontró una cuenta asociada a este correo electrónico"
                isSuccess = false
            }
        } catch (e: Exception) {
            message = "Error inesperado: ${e.message}"
            isSuccess = false
        } finally {
            isLoading = false
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recuperar Contraseña",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { 
                contentDescription = "Pantalla de recuperación de contraseña"
            }
        )
        Spacer(Modifier.height(16.dp))
        
        Text(
            text = "Ingresa tu correo electrónico y te enviaremos un enlace para restablecer tu contraseña",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.semantics { 
                contentDescription = "Instrucciones para recuperar contraseña"
            }
        )
        Spacer(Modifier.height(24.dp))
        
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Correo electrónico") },
            singleLine = true,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Campo de correo electrónico para recuperación de contraseña"
                }
        )
        Spacer(Modifier.height(20.dp))
        
        Button(
            onClick = viewModel::onSendRecoveryLink,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Altura mínima táctil
                .semantics { 
                    contentDescription = "Botón enviar enlace de recuperación"
                }
        ) { 
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Enviar Enlace",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Mensaje de estado
        viewModel.message?.let { message ->
            Spacer(Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isSuccess) 
                        MaterialTheme.colorScheme.primaryContainer 
                    else 
                        MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    color = if (viewModel.isSuccess) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .semantics { 
                            contentDescription = if (viewModel.isSuccess) 
                                "Éxito: $message" 
                            else 
                                "Error: $message"
                        }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(
            onClick = onBack,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .height(48.dp)
                .semantics { 
                    contentDescription = "Volver a la pantalla de inicio de sesión"
                }
        ) { 
            Text(
                text = "Volver al Inicio",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}




