package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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
        var search: Button = findViewById(R.id.button2)
        search.setOnClickListener{
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