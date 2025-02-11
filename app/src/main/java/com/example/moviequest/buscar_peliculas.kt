package com.example.moviequest

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import androidx.lifecycle.lifecycleScope // Importa lifecycleScope
import kotlinx.coroutines.launch // Importa launch

class buscar_peliculas : AppCompatActivity(), GenresBottomSheet.GenreSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_peliculas)

        findViewById<Button>(R.id.menuIcon).setOnClickListener {
            val bottomSheet = GenresBottomSheet()
            bottomSheet.setGenreSelectionListener(this)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        loadMovies() // Carga las películas desde la API
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            try {
                val response = MovieAPI.API().listMovies()
                if (response.isSuccessful) {
                    val movies = response.body() ?: emptyList()
                    initRecyclerViews(movies) // Inicializa los RecyclerViews con las películas de la API
                } else {
                    showErrorToast("Error al cargar películas: ${response.code()}")
                    initRecyclerViews(emptyList()) // Inicializa con listas vacías para evitar errores
                }
            } catch (e: Exception) {
                showErrorToast("Error al cargar películas: ${e.message}")
                initRecyclerViews(emptyList()) // Inicializa con listas vacías para evitar errores
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    override fun onGenresSelected(selectedGenres: List<String>) {
        if (selectedGenres.isNotEmpty()) {
            val genresText = selectedGenres.joinToString(", ")
            Toast.makeText(this, "Géneros seleccionados: $genresText", Toast.LENGTH_SHORT).show()
            // Aquí podrías implementar la lógica para filtrar películas por géneros seleccionados (si la API lo permite)
            // Por ahora, recargaremos todas las películas
            loadMovies() // Vuelve a cargar todas las peliculas al seleccionar generos (ejemplo)
        } else {
            Toast.makeText(this, "No se seleccionaron géneros", Toast.LENGTH_SHORT).show()
            loadMovies() // Vuelve a cargar todas las peliculas al deseleccionar generos (ejemplo)
        }
    }

    // Modifica initRecyclerViews para que acepte una lista de películas
    fun initRecyclerViews(movieList: List<Movie>){
        //millor valorades
        val rv_mv = findViewById<RecyclerView>(R.id.bestRatedRecycler)
        rv_mv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_mv.adapter = MovieAdapter(movieList) // Usa la lista de películas proporcionada

        //top 10
        val rv_t10 = findViewById<RecyclerView>(R.id.top10Recycler)
        rv_t10.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_t10.adapter = MovieAdapter(movieList) // Usa la lista de películas proporcionada

        //Misteri
        val rv_mt = findViewById<RecyclerView>(R.id.misteriRecycler)
        rv_mt.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_mt.adapter = MovieAdapter(movieList) // Usa la lista de películas proporcionada
    }
}