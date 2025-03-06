package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.google.android.material.navigation.NavigationView
import androidx.lifecycle.lifecycleScope // Importa lifecycleScope
import kotlinx.coroutines.launch // Importa launch

class user_activity : AppCompatActivity() {

    private var isDarkMode = false
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        // Recuperar el estado del tema si es que existe
        if (savedInstanceState != null) {
            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false)
        }

        setupNavigationDrawer()
        setupThemeToggle()
        loadMovies() // Carga las películas desde la API
        val usuario = application as Usuario
        var gmail: TextView
        gmail = findViewById(R.id.user_gmail)
        gmail.setText(usuario.gmail)
        var nom: TextView
        nom = findViewById(R.id.user_name)
        nom.setText(usuario.nom)

    }


    private fun loadMovies() {
        lifecycleScope.launch {
            try {
                val response = MovieAPI.API().listMovies()
                if (response.isSuccessful) {
                    val movies = response.body() ?: emptyList()
                    initRecyclerView(movies) // Inicializa el RecyclerView con las películas de la API
                } else {
                    showErrorToast("Error al cargar películas: ${response.code()}")
                    initRecyclerView(emptyList()) // Inicializa con lista vacía en caso de error
                }
            } catch (e: Exception) {
                showErrorToast("Error al cargar películas: ${e.message}")
                initRecyclerView(emptyList()) // Inicializa con lista vacía en caso de error
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun initRecyclerView(movieList: List<Movie>) {
        val rvUser = findViewById<RecyclerView>(R.id.userRecycler)
        rvUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvUser.adapter = MovieAdapter(movieList) { movie -> onMovieClicked(movie) } // Se pasa la función de clic
    }

    private fun onMovieClicked(movie: Movie) {
        Toast.makeText(this, "Película seleccionada: ${movie.nombre}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, pelicula_engran::class.java)
        intent.putExtra("MOVIE_NOM", movie.nombre)
        intent.putExtra("MOVIE_FOTO", movie.foto)
        intent.putExtra("MOVIE_DESC", movie.descripcion)
        startActivity(intent)
    }

    private fun setupNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        val openNavButton: ImageButton = findViewById(R.id.open_nav_button)

        openNavButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_info -> Toast.makeText(
                    this,
                    "Aquesta aplicació està dissenyada per als apassionats del cinema.",
                    Toast.LENGTH_LONG
                ).show()

                R.id.nav_partie -> Toast.makeText(
                    this,
                    "Aqui tens un video de com funcionen les parties!",
                    Toast.LENGTH_LONG
                ).show()

                R.id.nav_friend -> Toast.makeText(
                    this,
                    "Aqui tens un video de com agregar amics.",
                    Toast.LENGTH_LONG
                ).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupThemeToggle() {
        val themeToggle: ImageView = findViewById(R.id.theme_toggle)
        // Establecer la imagen inicial según el tema
        themeToggle.setImageResource(
            if (isDarkMode) R.drawable.toggle_dark
            else R.drawable.toggle_sun
        )

        themeToggle.setOnClickListener {
            isDarkMode = !isDarkMode
            Log.d("Theme", "isDarkMode: $isDarkMode") // Verificar el estado de isDarkMode
            applyTheme(isDarkMode)

            // Cambiar la imagen del toggle según el tema
            themeToggle.setImageResource(
                if (isDarkMode) R.drawable.toggle_dark
                else R.drawable.toggle_sun
            )
        }
    }

    private fun applyTheme(darkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_parties, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_partie -> showItems(4)
            R.id.nav_friend -> showItems(3)
        }
        return super.onContextItemSelected(item)
    }

    private fun showItems(count: Int) {
        val messages = listOf("Item 1", "Item 2").take(count)
        messages.forEach { showToast(it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isDarkMode", isDarkMode)
    }
}