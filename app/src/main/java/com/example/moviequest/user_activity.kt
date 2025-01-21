package com.example.moviequest

import android.widget.Button
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Habilitar pantalla completa sin márgenes

        setContentView(R.layout.activity_user)

        // Inicializar RecyclerView
        initRecyclerView()

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


