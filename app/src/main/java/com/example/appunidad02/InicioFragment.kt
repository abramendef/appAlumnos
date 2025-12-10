package com.example.appunidad02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appunidad02.database.Alumno
import com.example.appunidad02.database.FirebaseAlumnosRepo

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        android.util.Log.d("FB", "Entré a onViewCreated de InicioFragment")

        super.onViewCreated(view, savedInstanceState)

        // 🔹 PRUEBA RÁPIDA A FIREBASE
        val alumnoPrueba = Alumno(
            id = 0,
            matricula = "2024030001",
            nombre = "Alumno Prueba Firebase",
            domicilio = "Mazatlán",
            especialidad = "ITI",
            foto = "",
            syncState = 1,
            updatedAt = System.currentTimeMillis(),
            deleted = 0
        )

        val repo = FirebaseAlumnosRepo()
        repo.upsertAlumno(alumnoPrueba)
    }
}
