package com.example.moviequest;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu
import android.widget.Toast;

import androidx.annotation.NonNull;
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
                        Toast.makeText(this@Partiesglobal, "Crear partie", Toast.LENGTH_LONG).show()
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
}