package com.example.practica05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.practica05.ui.navigation.AppNavigation
import com.example.practica05.ui.theme.Practica05Theme

/**
 * Punto de entrada de la app. Aplica el tema y delega la UI al grafo de navegación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica05Theme {
                AppNavigation()
            }
        }
    }
}
