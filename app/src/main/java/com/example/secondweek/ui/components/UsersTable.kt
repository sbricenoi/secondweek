package com.example.secondweek.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.secondweek.data.User

/**
 * Componente de tabla para mostrar usuarios registrados
 * Optimizado para accesibilidad con TalkBack
 * @param users Lista de usuarios a mostrar
 */
@Composable
fun UsersTable(users: List<User>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { 
                contentDescription = "Tabla de usuarios registrados, ${users.size} usuarios en total"
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Encabezado de la tabla
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderCell(
                    text = "Nombre", 
                    modifier = Modifier.weight(1f),
                    description = "Columna de nombres de usuario"
                )
                HeaderCell(
                    text = "Email", 
                    modifier = Modifier.weight(1.3f),
                    description = "Columna de correos electrónicos"
                )
                HeaderCell(
                    text = "Accesibilidad", 
                    modifier = Modifier.weight(0.8f),
                    description = "Columna de preferencias de accesibilidad"
                )
            }
            
            // Filas de datos
            if (users.isNotEmpty()) {
                users.forEachIndexed { index, user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (index % 2 == 0) 
                                    MaterialTheme.colorScheme.surface 
                                else 
                                    MaterialTheme.colorScheme.surfaceContainer
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .semantics { 
                                contentDescription = "Usuario ${index + 1}: ${user.name}, ${user.email}, preferencia ${user.preference}"
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BodyCell(
                            text = user.name, 
                            modifier = Modifier.weight(1f)
                        )
                        BodyCell(
                            text = user.email, 
                            modifier = Modifier.weight(1.3f)
                        )
                        BodyCell(
                            text = user.preference, 
                            modifier = Modifier.weight(0.8f)
                        )
                    }
                    
                    // Divider entre filas (excepto la última)
                    if (index < users.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    }
                }
            } else {
                // Estado vacío
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No hay usuarios registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.semantics { 
                            contentDescription = "La tabla está vacía, no hay usuarios registrados"
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderCell(
    text: String, 
    modifier: Modifier = Modifier,
    description: String
) {
    Box(
        modifier = modifier.semantics { contentDescription = description }, 
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text, 
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BodyCell(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Text(
            text = text, 
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}




