package com.example.appunidad02

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.btnNavegador)

        if (savedInstanceState == null) {
            cambiarFragmento(InicioFragment())
        }

        bottomNavigationView.setOnItemSelectedListener {
            menuItem ->
            when(menuItem.itemId) {
                R.id.btnInicio -> {
                    cambiarFragmento(InicioFragment())
                    true
                }
                R.id.btnAlumnos -> {
                    cambiarFragmento(AlumnosFragment())
                    true
                }
                R.id.btnAcercade -> {
                    cambiarFragmento(AcercaFragment())
                    true
                }
                R.id.btnLista -> {
                    cambiarFragmento(ListaFragment())
                    true
                }
                R.id.btnSalir -> {
                    cambiarFragmento(SalirFragment())
                    true
                }
                else -> false
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    public fun cambiarFragmento(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.frmContenedor, fragment)
            .addToBackStack(null)
            .commit()


    }
}