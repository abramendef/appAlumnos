package com.example.appunidad02.database

import com.google.firebase.database.FirebaseDatabase

class FirebaseAlumnosRepo {

    // Referencia a /alumnos en Firebase Realtime Database
    private val dbRef =
        FirebaseDatabase.getInstance().getReference("alumnos")

    // Inserta o actualiza un alumno usando la matrícula como key
    fun upsertAlumno(alumno: Alumno) {
        if (alumno.matricula.isBlank()) return

        val data = mapOf(
            "matricula" to alumno.matricula,
            "nombre" to alumno.nombre,
            "carrera" to alumno.especialidad, // especialidad → carrera
            "domicilio" to alumno.domicilio,
            "foto" to alumno.foto
        )

        dbRef.child(alumno.matricula).setValue(data)
            .addOnSuccessListener { android.util.Log.d("FB", "✅ Guardado en Firebase") }
            .addOnFailureListener { e -> android.util.Log.e("FB", "❌ Error Firebase", e) }

    }

    // Elimina /alumnos/{matricula}
    fun deleteAlumno(matricula: String) {
        if (matricula.isBlank()) return
        dbRef.child(matricula).removeValue()
    }
}
