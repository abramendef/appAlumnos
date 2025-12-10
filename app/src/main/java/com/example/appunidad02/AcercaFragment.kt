package com.example.appunidad02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appunidad02.database.Alumno

class AcercaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_acerca, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgFoto = view.findViewById<ImageView>(R.id.imgFoto)
        val txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        val txtMatricula = view.findViewById<TextView>(R.id.txtMatricula)
        val txtEspecialidad = view.findViewById<TextView>(R.id.txtEspecialidad)
        val txtDomicilio = view.findViewById<TextView>(R.id.txtDomicilio)

        val imgQr = view.findViewById<ImageView>(R.id.imgQrAcercaDe)

        val miPerfil = Alumno(
            matricula = "2024030405",
            nombre = "Jesus Abraham Mendez Figueroa",
            especialidad = "Tec. Informacion",
            domicilio = "Club Real",
            foto = "https://raw.githubusercontent.com/abramendef/yo/0956e819c231a77facce0862aa7de9378232105a/IMG-20250814-WA0003.jpg"
        )

        txtNombre.text = miPerfil.nombre
        txtMatricula.text = "Matrícula: ${miPerfil.matricula}"
        txtEspecialidad.text = "Carrera: ${miPerfil.especialidad}"
        txtDomicilio.text = "Domicilio: ${miPerfil.domicilio}"
        Glide.with(this)
            .load(miPerfil.foto)
            .centerCrop()
            .into(imgFoto)

        try {
            // 2. Convertir a JSON usando la utilidad que creamos
            val jsonStr = QrUtils.alumnoToJson(miPerfil)

            // 3. Generar el Bitmap y ponerlo en la imagen automáticamente
            val bitmap = QrUtils.generarQrBitmap(jsonStr)
            imgQr.setImageBitmap(bitmap)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}