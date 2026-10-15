package com.example.practica05.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica05.data.PreferencesManager

private val carreras = listOf(
    "Ingeniería en Sistemas",
    "Ingeniería Industrial",
    "Contaduría Pública",
    "Derecho"
)

/**
 * Formulario de registro de estudiantes.
 *
 * Al abrirse recupera el último registro guardado en SharedPreferences y,
 * al presionar "Registrar", guarda los datos y navega al detalle.
 *
 * @param onRegistrar se invoca con los datos capturados cuando el formulario es válido.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistrar: (matricula: String, nombre: String, carrera: String, turno: String, activo: Boolean) -> Unit
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf(carreras[0]) }
    var turno by remember { mutableStateOf("Matutino") }
    var activo by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    // Recupera el último registro guardado al abrir la pantalla
    LaunchedEffect(Unit) {
        matricula = preferencesManager.getMatricula()
        carrera = preferencesManager.getCarrera().ifBlank { carreras[0] }
        turno = preferencesManager.getTurno()
        activo = preferencesManager.getActivo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registro de Estudiantes",
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Carrera - Exposed Dropdown Menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = carrera,
                onValueChange = {},
                readOnly = true,
                label = { Text("Carrera") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                carreras.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            carrera = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        // Turno - RadioButton
        Column {
            Text(text = "Turno:", fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = turno == "Matutino",
                    onClick = { turno = "Matutino" }
                )
                Text("Matutino")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = turno == "Vespertino",
                    onClick = { turno = "Vespertino" }
                )
                Text("Vespertino")
            }
        }

        // Estatus - Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Estatus: ${if (activo) "Activo" else "Inactivo"}", fontSize = 16.sp)
            Switch(checked = activo, onCheckedChange = { activo = it })
        }

        Button(
            onClick = {
                if (matricula.isNotBlank() && nombre.isNotBlank()) {
                    preferencesManager.saveRegistro(matricula, nombre, carrera, turno, activo)
                    onRegistrar(matricula, nombre, carrera, turno, activo)
                } else {
                    Toast.makeText(context, "Completa matrícula y nombre", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }

        // Borrar - limpia el formulario y el registro guardado
        OutlinedButton(
            onClick = {
                preferencesManager.clearRegistro()
                matricula = ""
                nombre = ""
                carrera = carreras[0]
                turno = "Matutino"
                activo = true
                Toast.makeText(context, "Datos borrados", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar")
        }
    }
}
