package com.example.appunidad02

import android.graphics.Bitmap
import com.example.appunidad02.database.Alumno
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONObject

object QrUtils {

    // 1. Convierte el objeto Alumno a un String JSON (formato del profe)
    fun alumnoToJson(alumno: Alumno): String {
        val json = JSONObject()
        try {
            json.put("matricula", alumno.matricula)
            json.put("nombre", alumno.nombre)
            json.put("carrera", alumno.especialidad) // El profe pide "carrera"
            json.put("domicilio", alumno.domicilio)
            json.put("foto", alumno.foto)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return json.toString()
    }

    // 2. Genera una imagen (Bitmap) a partir de un texto
    fun generarQrBitmap(texto: String, size: Int = 600): Bitmap? {
        return try {
            val barcodeEncoder = BarcodeEncoder()
            // Genera el código QR con formato UTF-8 implícito
            barcodeEncoder.encodeBitmap(texto, BarcodeFormat.QR_CODE, size, size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 3. Convierte un String JSON a un objeto Alumno
    fun jsonToAlumno(jsonStr: String): Alumno? {
        return try {
            val json = JSONObject(jsonStr)
            Alumno().apply {
                // Si el QR no tiene matrícula, fallará y retornará null
                matricula = json.getString("matricula")
                nombre = json.getString("nombre")
                especialidad = json.getString("carrera") // Recuerda: JSON dice "carrera"
                domicilio = json.getString("domicilio")
                foto = json.optString("foto", "") // optString por si viene vacío

                // Preparamos para guardar
                syncState = 1 // Pendiente de subir
                updatedAt = System.currentTimeMillis()
                deleted = 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}