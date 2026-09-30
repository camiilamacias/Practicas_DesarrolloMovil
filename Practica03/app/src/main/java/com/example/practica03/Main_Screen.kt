package com.example.practica03

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Práctica 03: Intents y Navegación",
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre.value,
                onValueChange = { nombre.value = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = correo.value,
                onValueChange = { correo.value = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 1. INTENT EXPLÍCITO: Navegar a ProfileActivity
            Button(
                onClick = {
                    val intent = Intent(context, ProfileActivity::class.java).apply {
                        putExtra("EXTRA_NOMBRE", nombre.value)
                        putExtra("EXTRA_CORREO", correo.value)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Perfil (Intent Explícito)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. INTENT IMPLÍCITO: Compartir datos
            Button(
                onClick = {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, "Hola, mi nombre es ${nombre.value} y mi correo es ${correo.value}")
                        type = "text/plain"
                    }
                    val chooser = Intent.createChooser(sendIntent, "Compartir datos usando:")
                    context.startActivity(chooser)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Compartir Datos (Intent Implícito)")
            }
        }
    }
}