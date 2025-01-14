package com.example.moviequest

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class user_activity : AppCompatActivity() {

    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Obtener el ImageView
        val themeToggle: ImageView = findViewById(R.id.theme_toggle)

        // Restaurar el estado del tema y la imagen
        if (savedInstanceState != null) {
            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false)
        }

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

            // Redibujar el ImageView para asegurar que la imagen se actualiza correctamente
            themeToggle.invalidate()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar el estado de isDarkMode
        outState.putBoolean("isDarkMode", isDarkMode)
    }
}

