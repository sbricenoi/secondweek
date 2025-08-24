package com.example.secondweek.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.secondweek.data.User

@Composable
fun UsersTable(users: List<User>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Tabla de usuarios" }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 8.dp)
        ) {
            HeaderCell(text = "Nombre", modifier = Modifier.weight(1f))
            HeaderCell(text = "Email", modifier = Modifier.weight(1.3f))
            HeaderCell(text = "Pref.", modifier = Modifier.weight(0.7f))
        }
        users.forEach { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                BodyCell(text = user.name, modifier = Modifier.weight(1f))
                BodyCell(text = user.email, modifier = Modifier.weight(1.3f))
                BodyCell(text = user.preference, modifier = Modifier.weight(0.7f))
            }
        }
        if (users.isEmpty()) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text("Sin usuarios registrados", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun HeaderCell(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
private fun BodyCell(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}


