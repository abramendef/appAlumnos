package com.example.appunidad02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appunidad02.database.Alumno
import com.example.appunidad02.database.AlumnoDB
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton



class ListaFragment : Fragment() {
    private lateinit var adapter: DbAdapter
    private lateinit var rcvLista: RecyclerView
    private lateinit var btnNuevof: FloatingActionButton
    private lateinit var listaAlumno: ArrayList<Alumno>
    private lateinit var db: AlumnoDB
    private var srv: SearchView? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        iniciarComponentes(view)
        eventosClic()
        cargarAlumnos()

        return view
    }


    fun iniciarComponentes(view : View){
        btnNuevof = view.findViewById(R.id.btnAgregarAlumno)
        srv = view.findViewById(R.id.srvAlumnos)
        rcvLista = view.findViewById(R.id.recId)
        rcvLista.layoutManager = LinearLayoutManager(requireContext())
        listaAlumno = ArrayList()
        adapter = DbAdapter(listaAlumno, requireContext())
        db = AlumnoDB(requireContext())
        rcvLista.adapter = adapter
    }

    fun eventosClic(){
        btnNuevof.setOnClickListener(View.OnClickListener {
            cambiarFragment()
        })
    }

    fun cargarAlumnos() {
        try {
            db.openDataBase()
            val nuevos = db.leerTodos()
            listaAlumno.clear()
            listaAlumno.addAll(nuevos)
            Toast.makeText(requireContext(), "cantidad reg " + listaAlumno.size, Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al cargar alumnos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }



    fun cambiarFragment() {
        (activity as? MainActivity)?.cambiarFragmento(AlumnosFragment())
    }


}