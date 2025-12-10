package com.example.appunidad02.database

import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase



class AlumnoDbHelper(Context: Context): SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ALUMNO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE ${DefinirTabla.Alumnos.TABLA} ADD COLUMN ${DefinirTabla.Alumnos.SYNC_STATE} INTEGER DEFAULT 0")
            db.execSQL("ALTER TABLE ${DefinirTabla.Alumnos.TABLA} ADD COLUMN ${DefinirTabla.Alumnos.UPDATED_AT} INTEGER DEFAULT 0")
            db.execSQL("ALTER TABLE ${DefinirTabla.Alumnos.TABLA} ADD COLUMN ${DefinirTabla.Alumnos.DELETED} INTEGER DEFAULT 0")
        }
    }
    companion object {
        private const val DATABASE_NAME = "sistemas"
        private const val INTEGER_TYPE = " INTERGER"
        private const val TEXT_TYPE = " TEXT"
        private const val COMMA = " ,"
        private const val DATABASE_VERSION = 2

        private const val SQL_CREATE_ALUMNO =
            "CREATE TABLE " + DefinirTabla.Alumnos.TABLA + " (" +
                    "${DefinirTabla.Alumnos.ID} INTEGER PRIMARY KEY," +
                    "${DefinirTabla.Alumnos.MATRICULA} TEXT," +
                    "${DefinirTabla.Alumnos.NOMBRE} TEXT," +
                    "${DefinirTabla.Alumnos.DOMICILIO} TEXT," +
                    "${DefinirTabla.Alumnos.ESPECIALIDAD} TEXT," +
                    "${DefinirTabla.Alumnos.FOTO} TEXT," +
                    "${DefinirTabla.Alumnos.SYNC_STATE} INTEGER DEFAULT 0," +
                    "${DefinirTabla.Alumnos.UPDATED_AT} INTEGER DEFAULT 0," +
                    "${DefinirTabla.Alumnos.DELETED} INTEGER DEFAULT 0" +
                    ")"

        private const val SQL_DELETE_ALUMNO = "DROP TABLE IF EXISTS ${DefinirTabla.Alumnos.TABLA}"

    }


}