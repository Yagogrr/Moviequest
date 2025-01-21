package com.example.moviequest

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.google.android.material.navigation.NavigationView

class user_activity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        initRecyclerView()
        // Inicializar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)
        val openNavButton: ImageButton = findViewById(R.id.open_nav_button)

        // Abrir el NavigationView al presionar el botón
        openNavButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Configurar acciones del NavigationView
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_info -> Toast.makeText(this, "Aquesta aplicació està dissenyada per als apassionats del cinema.", Toast.LENGTH_LONG).show()
                // Manejar otros ítems del menú aquí
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    fun initRecyclerView(){
        var rv_user = findViewById<RecyclerView>(R.id.userRecycler)
        rv_user.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rv_user.adapter = MovieAdapter(MovieProvider.movieList)
    }
}