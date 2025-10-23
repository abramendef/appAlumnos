package com.example.appunidad02.database

import java.io.Serializable

data class Alumno(
    var id: Int = 0,
    var matricula: String = "",
    var nombre: String = "",
    var domicilio: String = "",
    var especialidad: String = "",
    var foto: String = "") : Serializable


