package com.example.appunidad02

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appunidad02.database.Alumno

class DbAdapter(
    private var listaAlumno : ArrayList<Alumno>,
    private val contexto: Context
)   :RecyclerView.Adapter<DbAdapter.ViewHolder>(),
    View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(contexto)
    private var listener: View.OnClickListener? = null

    //declarar clase interna
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtAlumnoNombre)
        val txtMatricula: TextView = itemView.findViewById(R.id.txtMatricula)
        val txtCarrera: TextView = itemView.findViewById(R.id.txtCarrera)
        val idImagen: ImageView = itemView.findViewById(R.id.imgAlumno)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.alumno_item, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val alumno = listaAlumno[position]
        holder.txtMatricula.text = alumno.matricula
        holder.txtNombre.text = alumno.nombre
        holder.txtCarrera.text = alumno.especialidad

        try {
            val uri = Uri.parse(alumno.foto)
            holder.idImagen.setImageURI(uri)
        } catch (e: Exception) {
            holder.idImagen.setImageResource(R.drawable.alumno)
        }

    }

    override fun getItemCount(): Int {
        return listaAlumno.size
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }

    fun actualizarLista(lista: ArrayList<Alumno>) {
        listaAlumno = lista
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }
}






