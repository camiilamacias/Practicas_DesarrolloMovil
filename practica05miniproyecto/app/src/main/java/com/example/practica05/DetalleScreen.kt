package com.example.practica05

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetalleScreen(
    matricula: String,
    nombre: String,
    carrera: String,
    turno: String,
    activo: Boolean,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Confirmación de Registro",
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetalleItem("Matrícula:", matricula)
                DetalleItem("Nombre:", nombre)
                DetalleItem("Carrera:", carrera)
                DetalleItem("Turno:", turno)
                DetalleItem("Estatus:", if (activo) "Activo" else "Inactivo")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}

@Composable
private fun DetalleItem(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = value, fontSize = 18.sp)
    }
}
