package com.example.moviequest

import android.widget.Button
import android.os.Bundle
<<<<<<< HEAD
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.example.moviequest.MovieProvider

class user_activity : AppCompatActivity() {
    private var isDarkMode = false
=======
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.google.android.material.navigation.NavigationView

class user_activity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
>>>>>>> e7b51cb55253d71b6ca7bee85cf174e52d49f246

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Habilitar pantalla completa sin márgenes

        setContentView(R.layout.activity_user)

        // Inicializar RecyclerView
        initRecyclerView()
<<<<<<< HEAD

        // Recuperar el estado de isDarkMode si es necesario
        if (savedInstanceState != null) {
            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false)
        }

        // Obtener el ImageView para el cambio de tema
        val themeToggle: ImageView = findViewById(R.id.theme_toggle)

        // Establecer la imagen inicial dependiendo del estado del modo oscuro
        themeToggle.setImageResource(if (isDarkMode) R.drawable.toggle_dark else R.drawable.toggle_sun)

        // Configurar el clic en el ImageView para alternar entre sol y luna, y modo claro y oscuro
        themeToggle.setOnClickListener {
            // Alternar el estado de isDarkMode
            isDarkMode = !isDarkMode

            // Cambiar el tema y la imagen
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeToggle.setImageResource(R.drawable.toggle_dark)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeToggle.setImageResource(R.drawable.toggle_sun)
            }

            // No es necesario invalidar la vista, pero si deseas hacerlo:
            themeToggle.invalidate()  // Esto es opcional
=======
        // Inicializar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)
        val openNavButton: ImageButton = findViewById(R.id.open_nav_button)

        // Abrir el NavigationView al presionar el botón
        openNavButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Configurar acciones del NavigationView
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_info -> Toast.makeText(this, "Aquesta aplicació està dissenyada per als apassionats del cinema.", Toast.LENGTH_LONG).show()
                // Manejar otros ítems del menú aquí
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
>>>>>>> e7b51cb55253d71b6ca7bee85cf174e52d49f246
        }
    }

    // Inicializar RecyclerView
    private fun initRecyclerView() {
        val rv_user = findViewById<RecyclerView>(R.id.userRecycler)
        rv_user.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_user.adapter = MovieAdapter(MovieProvider.movieList)
    }

    // Guardar el estado al cambiar la configuración (por ejemplo, al girar la pantalla)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar el estado de isDarkMode
        outState.putBoolean("isDarkMode", isDarkMode)
    }
}


