package com.example.appunidad02.database

import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase



class AlumnoDbHelper(Context: Context): SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ALUMNO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ALUMNO)
        db?.execSQL(SQL_CREATE_ALUMNO)
    }
    companion object {
        private const val DATABASE_NAME = "sistemas"
        private const val DATABASE_VERSION = 1
        private const val INTERGER_TYPE = " INTERGER"
        private const val TEXT_TYPE = " TEXT"
        private const val COMMA = " ,"
        private const val SQL_CREATE_ALUMNO =
            "CREATE TABLE " + DefinirTabla.Alumnos.TABLA +
                    "(${DefinirTabla.Alumnos.ID} INTEGER PRIMARY KEY ," +
                    "${DefinirTabla.Alumnos.MATRICULA} $TEXT_TYPE $COMMA " +
                    "${DefinirTabla.Alumnos.NOMBRE} $TEXT_TYPE $COMMA " +
                    "${DefinirTabla.Alumnos.DOMICILIO} $TEXT_TYPE $COMMA " +
                    "${DefinirTabla.Alumnos.ESPECIALIDAD} $TEXT_TYPE $COMMA " +
                    "${DefinirTabla.Alumnos.FOTO} $TEXT_TYPE )"
        private const val SQL_DELETE_ALUMNO = "DROP TABLE IF EXISTS ${DefinirTabla.Alumnos.TABLA}"



    }


}