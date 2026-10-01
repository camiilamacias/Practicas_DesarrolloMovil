package com.example.practica05

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        // Pantalla 1 - Formulario de Registro
        composable("registro") {
            RegistroScreen(
                onRegistrar = { matricula, nombre, carrera, turno, activo ->
                    navController.navigate("detalle/$matricula/$nombre/$carrera/$turno/$activo")
                }
            )
        }

        // Pantalla 2 - Confirmación / Detalle
        composable(
            route = "detalle/{matricula}/{nombre}/{carrera}/{turno}/{activo}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("activo") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            DetalleScreen(
                matricula = backStackEntry.arguments?.getString("matricula") ?: "",
                nombre = backStackEntry.arguments?.getString("nombre") ?: "",
                carrera = backStackEntry.arguments?.getString("carrera") ?: "",
                turno = backStackEntry.arguments?.getString("turno") ?: "",
                activo = backStackEntry.arguments?.getBoolean("activo") ?: true,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
