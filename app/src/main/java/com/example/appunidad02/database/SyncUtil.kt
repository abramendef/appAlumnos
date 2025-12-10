package com.example.appunidad02.database

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

object SyncUtil {

    // Esta función programa la sincronización (Punto 4.4 del plan)
    fun iniciarSincronizacion(context: Context) {

        // 1. Restricción: Solo ejecutar si hay internet
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 2. Crear la petición de trabajo para nuestro Worker
        val syncRequest = OneTimeWorkRequest.Builder(SyncAlumnosWorker::class.java)
            .setConstraints(constraints)
            .build()

        // 3. Encolar trabajo con política KEEP
        // (Si ya hay una sincronización pendiente en cola, no agrega otra repetida)
        WorkManager.getInstance(context).enqueueUniqueWork(
            "syncAlumnos",
            ExistingWorkPolicy.KEEP,
            syncRequest
        )
    }
}