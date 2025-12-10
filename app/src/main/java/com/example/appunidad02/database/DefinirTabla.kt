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
        const val SYNC_STATE = "sync_state"   // 0=OK, 1=pendiente alta/edición, 2=pendiente borrar
        const val UPDATED_AT = "updated_at"   // timestamp (Long)
        const val DELETED = "deleted"         // 0=activo, 1=borrado lógico

    }
}