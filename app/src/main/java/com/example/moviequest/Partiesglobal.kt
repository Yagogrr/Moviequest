package com.example.moviequest;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Partiesglobal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parties_global)

        val popButton = findViewById<FloatingActionButton>(R.id.btn_desplegable)

        popButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this@Partiesglobal, view)
            popupMenu.inflate(R.menu.menu_parties)

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when(menuItem.itemId){
                    R.id.part_create ->{
                        mostrarPopupFormulario()
                        true
                    }

                    R.id.part_delete ->{
                        Toast.makeText(this@Partiesglobal, "Eliminar partie", Toast.LENGTH_LONG).show()
                        true
                    }
                    else ->{
                        false
                    }
                }
            }
            popupMenu.show();

        }
    }

    private fun mostrarPopupFormulario() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this) // o getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.popup_formulario, null)
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextTitol)
        val editTextDescripcio = dialogView.findViewById<EditText>(R.id.editTextDescripcio)

        builder.setTitle("Formulario de Partie")
        builder.setPositiveButton("Enviar") { dialog, which ->
            val nombre = editTextNombre.text.toString()
            val email = editTextDescripcio.text.toString()

            val mensaje = "Nombre: $nombre, Email: $email"
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}