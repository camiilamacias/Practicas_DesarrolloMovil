package com.example.practica05.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("EstudiantePreferences", Context.MODE_PRIVATE)

    companion object {
        const val KEY_MATRICULA = "key_matricula"
        const val KEY_NOMBRE = "key_nombre"
        const val KEY_CARRERA = "key_carrera"
        const val KEY_TURNO = "key_turno"
        const val KEY_ACTIVO = "key_activo"
    }

    fun saveRegistro(matricula: String, nombre: String, carrera: String, turno: String, activo: Boolean) {
        sharedPreferences.edit()
            .putString(KEY_MATRICULA, matricula)
            .putString(KEY_NOMBRE, nombre)
            .putString(KEY_CARRERA, carrera)
            .putString(KEY_TURNO, turno)
            .putBoolean(KEY_ACTIVO, activo)
            .apply()
    }

    fun getMatricula(): String = sharedPreferences.getString(KEY_MATRICULA, "") ?: ""

    fun getNombre(): String = sharedPreferences.getString(KEY_NOMBRE, "") ?: ""

    fun getCarrera(): String = sharedPreferences.getString(KEY_CARRERA, "") ?: ""

    fun getTurno(): String = sharedPreferences.getString(KEY_TURNO, "Matutino") ?: "Matutino"

    fun getActivo(): Boolean = sharedPreferences.getBoolean(KEY_ACTIVO, true)
}
