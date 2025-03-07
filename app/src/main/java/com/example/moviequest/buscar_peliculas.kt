package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class buscar_peliculas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_peliculas)

        // Configurar el botón del menú para mostrar el bottom sheet
        findViewById<Button>(R.id.menuIcon).setOnClickListener {
            val bottomSheet = GenresBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        loadMoviesByGenres() // Carga las películas por géneros desde la API
    }

    private fun loadMoviesByGenres() {
        lifecycleScope.launch {
            try {
                // Inicializar RecyclerViews con listas vacías primero para evitar NullPointerException
                initEmptyRecyclerViews()

                // Cargar datos para cada género en paralelo
                val terrorMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("terror") }
                val accionMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("accion") }
                val animacionMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("animacion") }

                // Esperar a que todas las peticiones terminen
                val responses = listOf(
                    terrorMoviesDeferred,
                    accionMoviesDeferred,
                    animacionMoviesDeferred
                ).awaitAll()

                // Procesar las respuestas y actualizar las RecyclerViews
                val terrorMovies = responses[0].body() ?: emptyList()
                val accionMovies = responses[1].body() ?: emptyList()
                val animacionMovies = responses[2].body() ?: emptyList()

                // Actualizar cada RecyclerView con su lista de películas correspondiente
                updateRecyclerView(findViewById(R.id.bestRatedRecycler), terrorMovies)
                updateRecyclerView(findViewById(R.id.top10Recycler), accionMovies)
                updateRecyclerView(findViewById(R.id.misteriRecycler), animacionMovies)

                // Mostrar mensaje de éxito o error si alguna de las listas está vacía
                if (terrorMovies.isEmpty() || accionMovies.isEmpty() || animacionMovies.isEmpty()) {
                    showErrorToast("Algunas categorías no tienen películas disponibles")
                }

            } catch (e: Exception) {
                showErrorToast("Error al cargar películas: ${e.message}")
            }
        }
    }

    private fun initEmptyRecyclerViews() {
        // Inicializar los RecyclerViews con listas vacías
        val rv_mv = findViewById<RecyclerView>(R.id.bestRatedRecycler)
        rv_mv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_mv.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }

        val rv_t10 = findViewById<RecyclerView>(R.id.top10Recycler)
        rv_t10.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_t10.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }

        val rv_mt = findViewById<RecyclerView>(R.id.misteriRecycler)
        rv_mt.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_mt.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }
    }

    private fun updateRecyclerView(recyclerView: RecyclerView, movies: List<Movie>) {
        recyclerView.adapter = MovieAdapter(movies) { movie -> onMovieClicked(movie) }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onMovieClicked(movie: Movie) {
        Toast.makeText(this, "Película seleccionada: ${movie.nombre}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, pelicula_engran::class.java)
        intent.putExtra("MOVIE_NOM", movie.nombre)
        intent.putExtra("MOVIE_FOTO", movie.foto)
        intent.putExtra("MOVIE_DESC", movie.descripcion)
        startActivity(intent)
    }
}