package com.example.appunidad02.database

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appunidad02.database.AlumnoDB
import com.example.appunidad02.database.FirebaseAlumnosRepo
import kotlinx.coroutines.tasks.await

class SyncAlumnosWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val db = AlumnoDB(applicationContext)
        val repo = FirebaseAlumnosRepo()

        return try {
            db.openDataBase()
            val pendientes = db.getAlumnosPendientesSync()

            for (alumno in pendientes) {
                if (alumno.deleted == 0 && alumno.syncState == 1) {
                    // Caso: Alta o Modificación -> Subir a Firebase
                    repo.upsertAlumno(alumno).await() // Espera a que Firebase confirme

                    // Si éxito, marcamos syncState = 0 (sincronizado)
                    db.updateSyncFields(alumno.id, 0)

                } else if (alumno.deleted == 1 && alumno.syncState == 2) {
                    // Caso: Borrado lógico -> Borrar en Firebase
                    repo.deleteAlumno(alumno.matricula).await()

                    // Si éxito, borramos definitivamente de SQLite para limpiar basura
                    db.deleteAlumnoDefinitivo(alumno.id)
                }
            }
            db.close()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry() // Si falla (ej. se va el internet a medias), reintenta luego
        }
    }
}