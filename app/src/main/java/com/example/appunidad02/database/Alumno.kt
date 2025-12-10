package com.example.appunidad02.database

import java.io.Serializable

data class Alumno(
    var id: Int = 0,
    var matricula: String = "",
    var nombre: String = "",
    var domicilio: String = "",
    var especialidad: String = "",
    var foto: String = "",

    // 🔹 campos para sincronización
    var syncState: Int = 1,          // 0=OK, 1=pendiente upsert, 2=pendiente borrar
    var updatedAt: Long = 0L,        // timestamp
    var deleted: Int = 0             // 0=activo, 1=borrado lógico
) : Serializable



