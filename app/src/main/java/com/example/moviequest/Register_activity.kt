package com.example.moviequest

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Register_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sortir: Button = findViewById(R.id.sortir)
        sortir.setOnClickListener{
            finish()
            System.exit(0)
        }

        val iniciSessio: Button = findViewById(R.id.iniciSessio)
        iniciSessio.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun showOptionsDialog(id: Long) {
        val options = arrayOf("Editar nom", "Eliminar")

        AlertDialog.Builder(this)
            .setTitle("Opcions")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarNom(id)
                    1 -> eliminarPartie(id)
                }
            }
            .show()
    }

    private fun editarNom(id: Long) {

    }
    private fun eliminarPartie(id: Long) {

    }

}