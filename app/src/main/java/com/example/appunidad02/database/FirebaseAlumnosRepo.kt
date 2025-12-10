package com.example.appunidad02.database

import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase

class FirebaseAlumnosRepo {
    private val dbRef = FirebaseDatabase.getInstance().getReference("alumnos")

    // Agregamos ": Task<Void>" al final para devolver el estado de la operación
    fun upsertAlumno(alumno: Alumno): Task<Void> {
        if (alumno.matricula.isBlank()) return com.google.android.gms.tasks.Tasks.forResult(null)

        val data = mapOf(
            "matricula" to alumno.matricula,
            "nombre" to alumno.nombre,
            "carrera" to alumno.especialidad,
            "domicilio" to alumno.domicilio,
            "foto" to alumno.foto
        )
        // Quitamos los listeners aquí para manejarlos en el Worker, o los dejamos, pero retornamos la Task
        return dbRef.child(alumno.matricula).setValue(data)
    }

    fun deleteAlumno(matricula: String): Task<Void> {
        if (matricula.isBlank()) return com.google.android.gms.tasks.Tasks.forResult(null)
        return dbRef.child(matricula).removeValue()
    }
}