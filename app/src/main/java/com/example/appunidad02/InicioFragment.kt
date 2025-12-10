package com.example.appunidad02

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.appunidad02.database.AlumnoDB
import com.example.appunidad02.database.SyncUtil
import com.google.zxing.integration.android.IntentIntegrator

class InicioFragment : Fragment() {

    private lateinit var btnEscanear: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEscanear = view.findViewById(R.id.btnEscanear)

        btnEscanear.setOnClickListener {
            iniciarEscaneo()
        }
    }

    private fun iniciarEscaneo() {
        // Configuramos el escáner de ZXing
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea el QR del Alumno")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    // Aquí recibimos el resultado de la cámara
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                // Tenemos el JSON en result.contents
                procesarQrLeido(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun procesarQrLeido(jsonStr: String) {
        // 1. Convertir JSON a Alumno
        val alumnoNuevo = QrUtils.jsonToAlumno(jsonStr)

        if (alumnoNuevo != null) {
            // 2. Mostrar Previsualización (Alerta)
            AlertDialog.Builder(requireContext())
                .setTitle("Alumno Detectado")
                .setMessage("Matrícula: ${alumnoNuevo.matricula}\n" +
                        "Nombre: ${alumnoNuevo.nombre}\n" +
                        "Carrera: ${alumnoNuevo.especialidad}\n\n" +
                        "¿Deseas guardarlo en la base de datos?")
                .setPositiveButton("Guardar") { _, _ ->
                    guardarEnBD(alumnoNuevo)
                }
                .setNegativeButton("Cancelar", null)
                .show()
        } else {
            Toast.makeText(requireContext(), "El QR no tiene formato válido de Alumno", Toast.LENGTH_LONG).show()
        }
    }

    private fun guardarEnBD(alumno: com.example.appunidad02.database.Alumno) {
        val db = AlumnoDB(requireContext())
        db.openDataBase()

        // Verificamos si ya existe para actualizar o insertar
        val existente = db.getAlumno(alumno.matricula)

        if (existente.id != 0) {
            // Ya existe -> Actualizamos usando el ID viejo
            db.actualizarAlumno(alumno, existente.id)
            Toast.makeText(requireContext(), "Alumno actualizado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            // No existe -> Insertamos
            db.insertarAlumno(alumno)
            Toast.makeText(requireContext(), "Alumno registrado correctamente", Toast.LENGTH_SHORT).show()
        }

        // 3. Sincronizar inmediatamente
        SyncUtil.iniciarSincronizacion(requireContext())
        db.close()
    }
}