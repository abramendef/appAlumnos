package com.example.appunidad02.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlin.apply

class AlumnoDB(private val context: Context) {

    private val dbHelper : AlumnoDbHelper = AlumnoDbHelper(context)
    private lateinit var db : SQLiteDatabase
    private val leerRegistro = arrayOf(
        DefinirTabla.Alumnos.ID,
        DefinirTabla.Alumnos.MATRICULA,
        DefinirTabla.Alumnos.NOMBRE,
        DefinirTabla.Alumnos.DOMICILIO,
        DefinirTabla.Alumnos.ESPECIALIDAD,
        DefinirTabla.Alumnos.FOTO,
        DefinirTabla.Alumnos.SYNC_STATE,
        DefinirTabla.Alumnos.UPDATED_AT,
        DefinirTabla.Alumnos.DELETED
    )


    fun openDataBase(){
        db = dbHelper.writableDatabase
    }

    fun insertarAlumno(alumno: Alumno) : Long {
        val values = ContentValues().apply {
            put(DefinirTabla.Alumnos.MATRICULA, alumno.matricula)
            put(DefinirTabla.Alumnos.NOMBRE, alumno.nombre)
            put(DefinirTabla.Alumnos.DOMICILIO, alumno.domicilio)
            put(DefinirTabla.Alumnos.ESPECIALIDAD, alumno.especialidad)
            put(DefinirTabla.Alumnos.FOTO, alumno.foto)

            // Nuevos campos
            put(DefinirTabla.Alumnos.SYNC_STATE, alumno.syncState)     // o 1 si es “pendiente subir”
            put(DefinirTabla.Alumnos.UPDATED_AT, alumno.updatedAt)     // o System.currentTimeMillis()
            put(DefinirTabla.Alumnos.DELETED, alumno.deleted)          // normalmente 0
        }
        return db.insert(DefinirTabla.Alumnos.TABLA, null, values)
    }

    fun actualizarAlumno(alumno: Alumno, id : Int) : Int {
        val values = ContentValues().apply {
            put(DefinirTabla.Alumnos.MATRICULA, alumno.matricula)
            put(DefinirTabla.Alumnos.NOMBRE, alumno.nombre)
            put(DefinirTabla.Alumnos.DOMICILIO, alumno.domicilio)
            put(DefinirTabla.Alumnos.ESPECIALIDAD, alumno.especialidad)
            put(DefinirTabla.Alumnos.FOTO, alumno.foto)

            // Nuevos campos
            put(DefinirTabla.Alumnos.SYNC_STATE, alumno.syncState)
            put(DefinirTabla.Alumnos.UPDATED_AT, alumno.updatedAt)
            put(DefinirTabla.Alumnos.DELETED, alumno.deleted)
        }
        return db.update(
            DefinirTabla.Alumnos.TABLA,
            values,
            "${DefinirTabla.Alumnos.ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun borrarAlumno(id : Int) : Int{
        return db.delete(DefinirTabla.Alumnos.TABLA, "${DefinirTabla.Alumnos.ID} = ?",
            arrayOf(id.toString()))
    }

    fun mostrarAlumno(cursor: Cursor) : Alumno {
        if (cursor.isAfterLast) return Alumno()

        return Alumno().apply {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.ID))
            matricula = cursor.getString(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.MATRICULA))
            nombre = cursor.getString(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.NOMBRE))
            domicilio = cursor.getString(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.DOMICILIO))
            especialidad = cursor.getString(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.ESPECIALIDAD))
            foto = cursor.getString(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.FOTO))

            syncState = cursor.getInt(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.SYNC_STATE))
            updatedAt = cursor.getLong(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.UPDATED_AT))
            deleted = cursor.getInt(cursor.getColumnIndexOrThrow(DefinirTabla.Alumnos.DELETED))
        }
    }

    fun getAlumno(matricula: String): Alumno {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DefinirTabla.Alumnos.TABLA,
            leerRegistro,
            "${DefinirTabla.Alumnos.MATRICULA} = ?",
            arrayOf(matricula),
            null,
            null,
            null
        )
        cursor.moveToFirst()
        val alumno = mostrarAlumno(cursor)
        cursor.close()
        return alumno
    }

    fun leerTodos(): ArrayList<Alumno> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DefinirTabla.Alumnos.TABLA,
            leerRegistro,
            null,
            null,
            null,
            null,
            null
        )
        val listAlumno = ArrayList<Alumno>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val alumno = mostrarAlumno(cursor)
            listAlumno.add(alumno)
            cursor.moveToNext()
        }
        cursor.close()
        return listAlumno
    }

    fun close() {
        dbHelper.close()
    }


}