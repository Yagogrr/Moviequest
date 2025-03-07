package com.example.moviequest;

import android.content.Intent
import android.os.Bundle;
import android.widget.TextView
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import kotlinx.coroutines.launch

class Filtres : AppCompatActivity() {

    private var selectedGenre: String? = null
    private var genreDisplayName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtres)

        // Get the selected genre from the intent
        selectedGenre = intent.getStringExtra("SELECTED_GENRE")
        genreDisplayName = intent.getStringExtra("GENRE_DISPLAY_NAME")

        // Update UI with the selected genre if needed
        val titleTextView = findViewById<TextView>(R.id.genreTitle)
        titleTextView?.text = genreDisplayName ?: "Todas las películas"

        loadMoviesByGenre() // Load movies based on genre
    }

    private fun loadMoviesByGenre() {
        lifecycleScope.launch {
            try {
                val response = if (selectedGenre != null) {
                    // Call the new endpoint with the selected genre
                    MovieAPI.API().getMoviesByGenre(selectedGenre!!)
                } else {
                    // If no genre selected, get all movies
                    MovieAPI.API().listMovies()
                }

                if (response.isSuccessful) {
                    val movies = response.body() ?: emptyList()
                    initRecyclerViews(movies)

                    // Show user feedback about the number of movies found
                    val message = if (movies.isEmpty()) {
                        "No se encontraron películas para $genreDisplayName"
                    } else {
                        "${movies.size} películas encontradas para $genreDisplayName"
                    }
                    showInfoToast(message)
                } else {
                    showErrorToast("Error al cargar películas: ${response.code()}")
                    initRecyclerViews(emptyList()) // Initialize with empty lists to avoid errors
                }
            } catch (e: Exception) {
                showErrorToast("Error al cargar películas: ${e.message}")
                initRecyclerViews(emptyList()) // Initialize with empty lists to avoid errors
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showInfoToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Initialize RecyclerViews with the movies
    private fun initRecyclerViews(movieList: List<Movie>) {
        val generoRv = findViewById<RecyclerView>(R.id.generoRv)

        // Use GridLayoutManager as specified in your XML
        generoRv.layoutManager = GridLayoutManager(this, 2)

        // Pass the movie list to the adapter with click handler
        generoRv.adapter = MovieAdapter(movieList) { movie ->
            onMovieClicked(movie)
        }
    }

    // Handler for movie click events
    private fun onMovieClicked(movie: Movie) {
        Toast.makeText(this, "Película seleccionada: ${movie.nombre}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, pelicula_engran::class.java)
        intent.putExtra("MOVIE_NOM", movie.nombre)
        intent.putExtra("MOVIE_FOTO", movie.foto)
        intent.putExtra("MOVIE_DESC", movie.descripcion)
        startActivity(intent)
    }
}