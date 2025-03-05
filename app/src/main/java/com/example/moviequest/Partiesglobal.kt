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
import kotlinx.coroutines.launch

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
                    else ->{
                        false
                    }
                }
            }
            popupMenu.show()

        }

        loadParties()
    }

    private fun loadParties() {
        lifecycleScope.launch {
            try {
                val response = PartieAPI.API().listParties()
                if (response.isSuccessful) {
                    val parties = response.body() ?: emptyList()
                    initRecyclerViews(parties) // Inicializa los RecyclerViews con las películas de la API
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

    fun initRecyclerViews(partieList: List<Partie>){
        val rv_pt = findViewById<RecyclerView>(R.id.partiesRv)
        //rv_pt.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv_pt.adapter = PartieAdapter(partieList)
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun mostrarPopupFormulario() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
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