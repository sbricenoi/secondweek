package com.example.secondweek.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.secondweek.data.User

/**
 * Componente de grilla para mostrar usuarios registrados
 * Optimizado para accesibilidad y diseño responsivo
 * @param users Lista de usuarios a mostrar
 * @param modifier Modificador para personalizar el componente
 */
@Composable
fun UsersGrid(users: List<User>, modifier: Modifier = Modifier) {
    if (users.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp), // Responsivo
            modifier = modifier
                .fillMaxWidth()
                .semantics { 
                    contentDescription = "Grilla de usuarios registrados, ${users.size} usuarios mostrados en formato de tarjetas"
                },
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(users) { user ->
                UserCard(user = user)
            }
        }
    } else {
        // Estado vacío
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay usuarios para mostrar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.semantics { 
                        contentDescription = "La grilla está vacía, no hay usuarios registrados para mostrar"
                    }
                )
            }
        }
    }
}

/**
 * Tarjeta individual para mostrar información de un usuario
 * @param user Usuario a mostrar
 */
@Composable
private fun UserCard(user: User) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .semantics { 
                contentDescription = "Tarjeta de usuario: ${user.name}, correo ${user.email}, preferencia de accesibilidad ${user.preference}"
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Nombre del usuario
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            // Email del usuario
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            // Preferencia de accesibilidad
            Surface(
                color = when (user.preference) {
                    "Alta" -> MaterialTheme.colorScheme.primaryContainer
                    "Media" -> MaterialTheme.colorScheme.secondaryContainer
                    else -> MaterialTheme.colorScheme.tertiaryContainer
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Accesibilidad: ${user.preference}",
                    style = MaterialTheme.typography.labelSmall,
                    color = when (user.preference) {
                        "Alta" -> MaterialTheme.colorScheme.onPrimaryContainer
                        "Media" -> MaterialTheme.colorScheme.onSecondaryContainer
                        else -> MaterialTheme.colorScheme.onTertiaryContainer
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


