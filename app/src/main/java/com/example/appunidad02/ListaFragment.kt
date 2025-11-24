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
        // En onCreateView, solo debemos inflar la vista. La lógica se mueve a onViewCreated.
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onViewCreated es el lugar seguro para inicializar vistas y cargar datos.
        iniciarComponentes(view)
        eventosClic()
        cargarAlumnos()
    }

    private fun iniciarComponentes(view : View){
        btnNuevof = view.findViewById(R.id.btnAgregarAlumno)
        srv = view.findViewById(R.id.srvAlumnos)
        rcvLista = view.findViewById(R.id.recId)
        rcvLista.layoutManager = LinearLayoutManager(requireContext())
        listaAlumno = ArrayList()
        adapter = DbAdapter(listaAlumno, requireContext())
        db = AlumnoDB(requireContext())
        rcvLista.adapter = adapter
    }

    private fun eventosClic(){
        btnNuevof.setOnClickListener(View.OnClickListener {
            cambiarFragment()
        })

        adapter.setOnClickListener(View.OnClickListener {
            val pos: Int = rcvLista.getChildAdapterPosition(it)
            val alumno = (rcvLista.adapter as DbAdapter).getItem(pos)
            val bundle = Bundle().apply {
                putSerializable("miAlumno", alumno)
            }
            val alumnoFragment = AlumnosFragment()
            alumnoFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.frmContenedor, alumnoFragment)
                .addToBackStack(null).commit()
            cambiarFragment()
        })

        srv?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun cargarAlumnos() {
        try {
            db.openDataBase()
            val nuevos = db.leerTodos()
            adapter.actualizarLista(nuevos)
            Toast.makeText(requireContext(), "Alumnos registrados: ${nuevos.size}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al cargar alumnos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun cambiarFragment() {
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.btnNavegador)
        bottom.menu.findItem(R.id.btnAlumnos).isChecked = true
        (activity as? MainActivity)?.cambiarFragmento(AlumnosFragment())
    }
}
