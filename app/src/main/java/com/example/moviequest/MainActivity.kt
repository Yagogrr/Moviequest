package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplashStart = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        screenSplashStart.setKeepOnScreenCondition{ false }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val registre: Button = findViewById(R.id.button1)
        registre.setOnClickListener{
            val intent = Intent(this, Register_activity::class.java)
            startActivity(intent)
        }
        var nom: TextView
        nom = findViewById(R.id.textNom)
        var gmail: TextView
        gmail = findViewById(R.id.gmail2)

        val idText = findViewById<TextInputEditText>(R.id.textId).text.toString()
        val id = idText.toIntOrNull() ?: 0

        var search: Button = findViewById(R.id.button2)
        search.setOnClickListener{
            val usuario = application as Usuario
            usuario.setDatosUsuario(id.toString().toInt(),nom.text.toString(), gmail.text.toString())
            var intent = Intent(this,buscar_peliculas::class.java)
            startActivity(intent)
        }

        var sortir : Button = findViewById(R.id.button3)
        sortir.setOnClickListener{
            finish()
            System.exit(0)
        }

    }
}