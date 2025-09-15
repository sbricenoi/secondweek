package com.example.secondweek.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
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

// Constante para el máximo de usuarios permitidos
private const val MAX_USERS = 5

/**
 * ViewModel para la pantalla de registro de usuarios
 * Gestiona el estado del formulario y la lógica de validación
 */
class RegisterViewModel : ViewModel() {
    // Estado del formulario
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirm by mutableStateOf("")
        private set
    var termsAccepted by mutableStateOf(false)
        private set
    var preference by mutableStateOf("Alta")
        private set
    var gender by mutableStateOf("Prefiero no decir")
        private set
    
    // Estado de la UI
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var successMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var users: List<User> by mutableStateOf(UserRepository.getAllUsers())
        private set

    // Funciones para actualizar el estado
    fun updateName(value: String) { name = value }
    fun updateEmail(value: String) { email = value }
    fun updatePassword(value: String) { password = value }
    fun updateConfirm(value: String) { confirm = value }
    fun updateTermsAccepted(value: Boolean) { termsAccepted = value }
    fun updatePreference(value: String) { preference = value }
    fun updateGender(value: String) { gender = value }

    /**
     * Valida si un email tiene formato correcto
     */
    private fun isValidEmail(text: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(text.trim()).matches()

    /**
     * Limpia los mensajes de estado
     */
    fun clearMessages() {
        errorMessage = null
        successMessage = null
    }

    /**
     * Procesa el registro de un nuevo usuario
     * Valida todos los campos y registra el usuario si es válido
     */
    fun onRegister() {
        clearMessages()
        isLoading = true

        try {
            // Validaciones del formulario
            when {
                name.isBlank() -> {
                    errorMessage = "El nombre es requerido"
                    return
                }
                name.trim().length < 2 -> {
                    errorMessage = "El nombre debe tener al menos 2 caracteres"
                    return
                }
                !isValidEmail(email) -> {
                    errorMessage = "Formato de email inválido"
                    return
                }
                password.length < 6 -> {
                    errorMessage = "La contraseña debe tener al menos 6 caracteres"
                    return
                }
                password != confirm -> {
                    errorMessage = "Las contraseñas no coinciden"
                    return
                }
                !termsAccepted -> {
                    errorMessage = "Debe aceptar los términos y condiciones"
                    return
                }
            }

            // Intentar crear el usuario
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

            result.onSuccess {
                successMessage = "Usuario registrado exitosamente (${UserRepository.getUserCount()}/${5})"
                users = UserRepository.getAllUsers()
                // Limpiar formulario después del registro exitoso
                clearForm()
            }.onFailure { exception ->
                errorMessage = exception.message ?: "Error desconocido al registrar usuario"
            }
        } catch (e: Exception) {
            errorMessage = "Error inesperado: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    /**
     * Limpia todos los campos del formulario
     */
    private fun clearForm() {
        name = ""
        email = ""
        password = ""
        confirm = ""
        termsAccepted = false
        preference = "Alta"
        gender = "Prefiero no decir"
    }

    /**
     * Actualiza la lista de usuarios (útil para refrescar la vista)
     */
    fun refreshUsers() {
        users = UserRepository.getAllUsers()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onBack: () -> Unit, viewModel: RegisterViewModel = viewModel()) {
    val preferences = listOf("Alta", "Media", "Baja")
    val genders = listOf("Femenino", "Masculino", "Prefiero no decir")

    val scrollState = rememberScrollState()
    // Contenedor con scroll para acceder a tabla y grilla en pantallas pequeñas
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Registro", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = viewModel::updateName,
            label = { Text("Nombre completo") },
            singleLine = true,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Campo de nombre completo, requerido"
                }
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Correo electrónico") },
            singleLine = true,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Campo de correo electrónico, debe ser único y válido"
                }
        )
        Spacer(Modifier.height(12.dp))
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
                    contentDescription = "Campo de contraseña, mínimo 6 caracteres"
                }
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.confirm,
            onValueChange = viewModel::updateConfirm,
            label = { Text("Confirmar contraseña") },
            singleLine = true,
            enabled = !viewModel.isLoading,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Confirmar contraseña, debe coincidir con la anterior"
                }
        )
        Spacer(Modifier.height(12.dp))

        // ComboBox (ExposedDropdownMenuBox) para preferencias de accesibilidad
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = viewModel.preference,
                onValueChange = {},
                readOnly = true,
                enabled = !viewModel.isLoading,
                label = { Text("Nivel de accesibilidad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .semantics { 
                        contentDescription = "Selector de nivel de accesibilidad: ${viewModel.preference}"
                    }
            )
            ExposedDropdownMenu(
                expanded = expanded, 
                onDismissRequest = { expanded = false }
            ) {
                preferences.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) }, 
                        onClick = {
                            viewModel.updatePreference(option)
                            expanded = false
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "Opción de accesibilidad: $option"
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        // Radio buttons para género
        Text(
            text = "Género",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.semantics { 
                contentDescription = "Sección de selección de género"
            }
        )
        Spacer(Modifier.height(8.dp))
        genders.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = viewModel.gender == option,
                        onClick = { viewModel.updateGender(option) },
                        enabled = !viewModel.isLoading
                    )
                    .semantics { 
                        contentDescription = "Opción de género: $option"
                    }
            ) {
                RadioButton(
                    selected = viewModel.gender == option,
                    onClick = { viewModel.updateGender(option) },
                    enabled = !viewModel.isLoading,
                    modifier = Modifier.size(48.dp) // Tamaño táctil mínimo
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.termsAccepted,
                onCheckedChange = viewModel::updateTermsAccepted,
                enabled = !viewModel.isLoading,
                modifier = Modifier
                    .size(48.dp) // Tamaño táctil mínimo
                    .semantics { 
                        contentDescription = "Casilla para aceptar términos y condiciones, requerido"
                    }
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Acepto los términos y condiciones de uso",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = viewModel::onRegister,
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Altura mínima táctil
                .semantics { 
                    contentDescription = "Botón registrarse, procesa el formulario de registro"
                }
        ) { 
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Registrarse",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Mensajes de estado
        viewModel.errorMessage?.let { message ->
            Spacer(Modifier.height(16.dp))
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
                            contentDescription = "Error: $message"
                        }
                )
            }
        }
        
        viewModel.successMessage?.let { message ->
            Spacer(Modifier.height(16.dp))
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
                            contentDescription = "Éxito: $message"
                        }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(
            onClick = onBack,
            modifier = Modifier
                .height(48.dp)
                .semantics { 
                    contentDescription = "Volver a la pantalla anterior"
                }
        ) { 
            Text(
                text = "Volver",
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Sección de usuarios registrados
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Usuarios Registrados (${viewModel.users.size}/$MAX_USERS)",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { 
                contentDescription = "Sección de usuarios registrados, ${viewModel.users.size} de $MAX_USERS usuarios"
            }
        )
        
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Vista de tabla",
            style = MaterialTheme.typography.bodyLarge
        )
        UsersTable(users = viewModel.users)

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Vista de grilla",
            style = MaterialTheme.typography.bodyLarge
        )
        UsersGrid(
            users = viewModel.users, 
            modifier = Modifier.height(200.dp)
        )
    }
}


