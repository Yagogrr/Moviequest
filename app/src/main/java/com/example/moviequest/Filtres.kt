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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.example.moviequest.adapter.PartieAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Filtres : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtres)

        val popButton = findViewById<FloatingActionButton>(R.id.btn_desplegable)

        popButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this@Filtres, view)
            popupMenu.inflate(R.menu.menu_parties)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.part_create -> {
                        mostrarPopupFormulario()
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
        rv_pt.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Pasar la función de clic largo al adapter
        rv_pt.adapter = PartieAdapter(partieList) { partie ->
            onPartieLongClicked(partie) // Llamar al handler cuando se mantiene pulsado
        }
    }

    // Acción que se ejecuta al mantener pulsado un ítem
    private fun onPartieLongClicked(partie: Partie) {
        // Crear un diálogo de confirmación
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Partie")
        builder.setMessage("¿Estás seguro de que deseas eliminar esta partie?")

        // Acción al presionar "Sí"
        builder.setPositiveButton("Sí") { dialog, which ->
            // Eliminar la partie de la lista
            deletePartie(partie)
            dialog.dismiss() // Cerrar el diálogo
        }

        // Acción al presionar "No"
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss() // Cerrar el diálogo sin hacer nada
        }

        // Mostrar el diálogo
        builder.create().show()
    }

    private fun deletePartie(partie: Partie) {
        lifecycleScope.launch {
            try {
                val response = PartieAPI.API().deletePartie(partie.id) // Llamar a la API para eliminar la partie usando el id
                if (response.isSuccessful) {
                    // Si la eliminación fue exitosa, actualizamos la lista de parties
                    loadParties()
                    Toast.makeText(this@Filtres, "Partie eliminada", Toast.LENGTH_SHORT).show()
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
/*
        // Si 'partie' no es null, se llenan los campos con los valores actuales de la 'partie'
        if (partie != null) {
            editTextNombre.setText(partie.titulo)
            editTextDescripcio.setText(partie.descripcion)
        }
*/
        builder.setTitle("Formulario de Partie")
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
                        println("Partie creada exitosamente: $partieCreada")
                    } else {
                        println("Error al crear la partie. Código de error: ${response.code()}")
                        println("Mensaje de error: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    println("Excepción al crear la partie: ${e.message}")
                    e.printStackTrace()
                }
            }

            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}