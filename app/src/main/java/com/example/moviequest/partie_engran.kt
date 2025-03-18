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

    private fun editarPartie(partie: Partie, originalTitle: String? = null, originalDesc: String? = null) {
        val currentTitle = originalTitle ?: findViewById<TextView>(R.id.textViewNombre).text.toString()
        val currentDesc = originalDesc ?: findViewById<TextView>(R.id.textViewDescripcion).text.toString()

        // Vemos que hay que actualizar
        val titleChanged = partie.titulo != currentTitle
        val descChanged = partie.descripcion != currentDesc

        CoroutineScope(Dispatchers.IO).launch {
            try {
                var titleUpdateSuccess = true
                var descUpdateSuccess = true

                //Solo cambia el titulo si solo se ha cambiado el titulo
                if (titleChanged) {
                    val titleResponse = PartieAPI.API().editPartie(
                        partie.id,
                        TituloUpdate(titulo = partie.titulo)
                    )
                    titleUpdateSuccess = titleResponse.isSuccessful
                }

                // Solo cambia la descripción si solo se ha cambiado la descripción
                if (descChanged) {
                    val descResponse = PartieAPI.API().editPartie(
                        partie.id,
                        DescripcionUpdate(descripcion = partie.descripcion)
                    )
                    descUpdateSuccess = descResponse.isSuccessful
                }

                withContext(Dispatchers.Main) {
                    if (titleUpdateSuccess && descUpdateSuccess) {
                        // Update UI with the new data
                        if (titleChanged) {
                            findViewById<TextView>(R.id.textViewNombre).text = partie.titulo
                        }
                        if (descChanged) {
                            findViewById<TextView>(R.id.textViewDescripcion).text = partie.descripcion
                        }
                        Toast.makeText(this@partie_engran, "Partie actualizada correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        // Handle errors
                        val errorMessage = when {
                            !titleUpdateSuccess -> "Error al actualizar título"
                            !descUpdateSuccess -> "Error al actualizar descripción"
                            else -> "Error desconocido"
                        }
                        Toast.makeText(this@partie_engran, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@partie_engran, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}