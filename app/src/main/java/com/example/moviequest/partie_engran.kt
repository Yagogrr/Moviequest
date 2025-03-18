package com.example.moviequest;

import android.os.Bundle;
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class partie_engran : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.partie_engran)

        val partieName = intent.getStringExtra("PARTIE_NOM")
        val partieDesc = intent.getStringExtra("PARTIE_DESC")
        val partieId = intent.getStringExtra("PARTIE_ID")

        val titleTextView = findViewById<TextView>(R.id.textViewNombre)
        val descView = findViewById<TextView>(R.id.textViewDescripcion)
        val editIcon = findViewById<ImageView>(R.id.iconoLapiz)
        editIcon.setOnClickListener { view ->
            mostrarPopupFormulario(partieId)

        }

        titleTextView.text = partieName
        descView.text = partieDesc
    }

    private fun mostrarPopupFormulario(partieId: String?) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.popup_formulario, null)
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextTitol)
        val editTextDescripcio = dialogView.findViewById<EditText>(R.id.editTextDescripcio)

        // Obtener los valores actuales de los TextView
        val tituloActual = findViewById<TextView>(R.id.textViewNombre).text.toString()
        val descripcionActual = findViewById<TextView>(R.id.textViewDescripcion).text.toString()

        // Establecer los valores actuales en los campos del formulario
        editTextNombre.setText(tituloActual)
        editTextDescripcio.setText(descripcionActual)

        builder.setTitle("Formulari de Partie")
        builder.setPositiveButton("Enviar") { dialog, which ->
            val titol = editTextNombre.text.toString()
            val descripcio = editTextDescripcio.text.toString()
            val usuari = application as Usuario
            val userId = usuari.id
            if (partieId==null){
                Toast.makeText(this@partie_engran, "Error al editar la partie", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            val novaPartie = Partie(partieId.toInt(), titol, descripcio, userId.toString())
            editarPartie(novaPartie)


            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel·lar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun editarPartie(partie: Partie) {
        // Crear un EditText para ingresar la nueva descripción
        val input = EditText(this@partie_engran)
        input.hint = "Introdueix la nova descripció"

        // Crear un AlertDialog con un campo de entrada
        val dialog = AlertDialog.Builder(this@partie_engran)
            .setTitle("Editar descripció de la Partie")
            .setMessage("Introdueix la nueva descripció de la Partie")
            .setView(input)
            .setPositiveButton("Aceptar") { dialog, which ->
                val nuevaDescripcion = input.text.toString()

                // Validar si la nueva descripción no está vacía
                if (nuevaDescripcion.isNotBlank()) {
                    // Crear un objeto DescripcionUpdate
                    val descripcionUpdate = DescripcionUpdate(descripcion = nuevaDescripcion)

                    // Llamar a la API con la nueva descripción
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = PartieAPI.API().editPartie(partie.id, descripcionUpdate)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@partie_engran, "Partie actualizada", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@partie_engran, "Error al actualizar la partie", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@partie_engran, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        // Mostrar el diálogo
        dialog.show()
    }
}