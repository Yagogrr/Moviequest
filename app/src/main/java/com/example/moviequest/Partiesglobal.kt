package com.example.moviequest

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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.example.moviequest.adapter.PartieAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.GridLayoutManager // Import GridLayoutManager
import kotlinx.coroutines.withContext

class Partiesglobal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parties_global)

        val popButton = findViewById<FloatingActionButton>(R.id.btn_desplegable)

        popButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this@Partiesglobal, view)
            popupMenu.inflate(R.menu.menu_parties)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.part_create -> {
                        mostrarPopupFormulario() // No hay 'partie' para crear
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()

        }

        loadParties() // Cargar las parties desde la API
    }

    private fun loadParties() {
        lifecycleScope.launch {
            try {
                val response = PartieAPI.API().listParties()
                if (response.isSuccessful) {
                    val parties = response.body() ?: emptyList()
                    initRecyclerViews(parties) // Inicializa los RecyclerViews con las parties de la API
                } else {
                    showErrorToast("Error al cargar parties: ${response.code()}")
                    initRecyclerViews(emptyList()) // Inicializa con listas vacías para evitar errores
                }
            } catch (e: Exception) {
                showErrorToast("Error al cargar parties: ${e.message}")
                initRecyclerViews(emptyList()) // Inicializa con listas vacías para evitar errores
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    // Inicializa los RecyclerViews con las parties
    private fun initRecyclerViews(partieList: List<Partie>) {
        val rv_pt = findViewById<RecyclerView>(R.id.partiesRv)
        // Configura GridLayoutManager con 2 columnas
        rv_pt.layoutManager = GridLayoutManager(this, 2)

        // Pasar la función de clic largo al adapter
        rv_pt.adapter = PartieAdapter(partieList) { partie ->
            onPartieLongClicked(partie) // Llamar al handler cuando se mantiene pulsado
        }
    }

    // Acción que se ejecuta al mantener pulsado un ítem
    private fun onPartieLongClicked(partie: Partie) {
        // Crear un diálogo con opciones
        val options = arrayOf("Editar", "Eliminar")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una opción")

        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> editarPartie(partie)
                1 -> deletePartieWithConfirmation(partie)
                2 -> editarNomPartie(partie)
            }
        }
        builder.show()
    }
    private fun editarNomPartie(partie : Partie){

    }
    private fun editarPartie(partie: Partie) {
        // Crear un EditText para ingresar la nueva descripción
        val input = EditText(this@Partiesglobal)
        input.hint = "Introdueix la nova descripció"

        // Crear un AlertDialog con un campo de entrada
        val dialog = AlertDialog.Builder(this@Partiesglobal)
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
                                loadParties() // Recargar la lista de parties
                                Toast.makeText(this@Partiesglobal, "Partie actualizada", Toast.LENGTH_SHORT).show()
                            } else {
                                showErrorToast("Error al actualizar la partie: ${response.code()}")
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@Partiesglobal, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        // Mostrar el diálogo
        dialog.show()
    }


    private fun deletePartieWithConfirmation(partie: Partie) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación de eliminación")
        builder.setMessage("Segur que vols eliminar la partie '${partie.titulo}'?")

        builder.setPositiveButton("Sí, eliminar") { dialog, _ ->
            deletePartie(partie)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel·lar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
    private fun deletePartie(partie: Partie) {
        lifecycleScope.launch {
            try {
                val response = PartieAPI.API().deletePartie(partie.id) // Llamar a la API para eliminar la partie
                if (response.isSuccessful) {
                    loadParties() // Recargar la lista de parties
                    Toast.makeText(this@Partiesglobal, "Partie eliminada", Toast.LENGTH_SHORT).show()
                } else {
                    showErrorToast("Error al eliminar la partie: ${response.code()}")
                }
            } catch (e: Exception) {
                showErrorToast("Error al eliminar la partie: ${e.message}")
            }
        }
    }
    private fun mostrarPopupFormulario() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.popup_formulario, null)
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextTitol)
        val editTextDescripcio = dialogView.findViewById<EditText>(R.id.editTextDescripcio)

        builder.setTitle("Formulari de Partie")
        builder.setPositiveButton("Enviar") { dialog, which ->
            val titol = editTextNombre.text.toString()
            val descripcio = editTextDescripcio.text.toString()
            val usuari = application as Usuario
            val userId = usuari.id
            val partieAPI = PartieAPI()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = partieAPI.createPartie(titol, descripcio, userId)

                    if (response.isSuccessful) {
                        val partieCreada = response.body()
                        loadParties()
                        println("Partie creada correctament: $partieCreada")
                    } else {
                        println("Error al crear la partie. Error: ${response.code()}")
                        println("Missatge d'error: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                    e.printStackTrace()
                }
            }

            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel·lar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}