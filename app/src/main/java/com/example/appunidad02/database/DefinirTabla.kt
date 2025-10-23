package com.example.appunidad02.database

import android.provider.BaseColumns

class DefinirTabla {
    object Alumnos: BaseColumns{
        const val TABLA = "alumnos"
        const val ID = "id"
        const val MATRICULA = "matricula"
        const val NOMBRE = "nombre"
        const val DOMICILIO = "domicilio"
        const val ESPECIALIDAD = "especialidad"
        const val FOTO = "foto"
    }
}