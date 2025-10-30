package com.example.appunidad02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var btnAgregarAlumno : FloatingActionButton
private lateinit var bottomNavigationView: BottomNavigationView


class ListaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        iniciarComponentes(view)
        eventosClic()

        return view
    }


    fun iniciarComponentes(view : View){
        btnAgregarAlumno = view.findViewById(R.id.btnAgregarAlumno)
        bottomNavigationView = view.findViewById(R.id.btnNavegador)


    }

    fun eventosClic(){
        btnAgregarAlumno.setOnClickListener{
            cambiarFragment(AlumnosFragment())
        }
    }

    public fun cambiarFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.frmContenedor, fragment).addToBackStack(null).commit()
        val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
            R.id.btnNavegador
        )
        bottomNav.selectedItemId = R.id.btnAlumnos
    }

}