package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.util.Log // Añadir import para Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import retrofit2.Response

// Asumiendo que tu clase Movie tiene esta estructura (o similar con 'genero'):
// data class Movie(
//    val nombre: String,
//    val foto: String,
//    val descripcion: String,
//    val genero: String // <--- ¡IMPORTANTE! Necesitas este campo
//    // ... otros campos si los hay
// )

class buscar_peliculas : AppCompatActivity() {
    // ... (resto del código onCreate, getMovie, loadMoviesByGenres, etc. sin cambios)

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
        val buscarPeliBarra = findViewById<EditText>(R.id.searchEditText)
        val buscarPeliBtn = findViewById<ImageView>(R.id.searchIcon)
        buscarPeliBtn.setOnClickListener {
            val textoABuscar = buscarPeliBarra.text.toString()

            if (textoABuscar.isNotEmpty()) {
                getMovie(textoABuscar)
            } else {
                Toast.makeText(this, "Por favor, introduce un texto para buscar", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getMovie(string: String) {
        lifecycleScope.launch {
            try {
                // Asegúrate que getMovieInd devuelve una película CON su género
                val response : Response<Movie> = MovieAPI.API().getMovieInd(string)
                if (response.isSuccessful) {
                    val movie: Movie? = response.body()
                    onMovieClicked(movie) // Llama a onMovieClicked con la película obtenida
                } else {
                    showErrorToast("Error al buscar la peli: ${response.code()}")
                }
            } catch (e: Exception) {
                showErrorToast("Error al buscar la peli: ${e.message}")
            }
        }
    }

    private fun loadMoviesByGenres() {
        lifecycleScope.launch {
            try {
                initEmptyRecyclerViews()

                // Asumiendo que getMoviesByGenre devuelve películas CON su género
                val terrorMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("terror") }
                val accionMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("accion") }
                val animacionMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("animacion") }
                // Añadir fantasía si es necesario o gestionarlo de otra forma
                // val fantasiaMoviesDeferred = async { MovieAPI.API().getMoviesByGenre("fantasia") }

                val responses = listOf(
                    terrorMoviesDeferred,
                    accionMoviesDeferred,
                    animacionMoviesDeferred
                    // fantasiaMoviesDeferred
                ).awaitAll()

                val terrorMovies = responses[0].body() ?: emptyList()
                val accionMovies = responses[1].body() ?: emptyList()
                val animacionMovies = responses[2].body() ?: emptyList()
                // val fantasiaMovies = responses[3].body() ?: emptyList()


                updateRecyclerView(findViewById(R.id.terrorRv), terrorMovies)
                updateRecyclerView(findViewById(R.id.accioRv), accionMovies)
                updateRecyclerView(findViewById(R.id.animacioRv), animacionMovies)
                // Actualizar RV de fantasía si existe
                // updateRecyclerView(findViewById(R.id.fantasiaRv), fantasiaMovies) // Necesitarías un RecyclerView para fantasía

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
        // Asegúrate que el MovieAdapter pasa la película completa (con género) al lambda onClick
        val rv_terror = findViewById<RecyclerView>(R.id.terrorRv)
        rv_terror.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_terror.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }

        val rv_accion = findViewById<RecyclerView>(R.id.accioRv)
        rv_accion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_accion.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }

        val rv_animacion = findViewById<RecyclerView>(R.id.animacioRv)
        rv_animacion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_animacion.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }

        // Añadir inicialización para otros géneros si tienes más RecyclerViews
        // val rv_fantasia = findViewById<RecyclerView>(R.id.fantasiaRv)
        // rv_fantasia.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // rv_fantasia.adapter = MovieAdapter(emptyList()) { movie -> onMovieClicked(movie) }
    }

    private fun updateRecyclerView(recyclerView: RecyclerView, movies: List<Movie>) {
        // Asegúrate que el MovieAdapter pasa la película completa (con género) al lambda onClick
        recyclerView.adapter = MovieAdapter(movies) { movie -> onMovieClicked(movie) }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun onMovieClicked(movie: Movie?) {
        if (movie != null) {
            Toast.makeText(this, "Película seleccionada: ${movie.nombre}", Toast.LENGTH_SHORT).show()
            when (movie.genero.lowercase()) {
                "acción" -> Usuario.estadistica.generes[0]++ // Índice 0 para Acción
                "animación" -> Usuario.estadistica.generes[1]++ // Índice 1 para Animación
                "fantasia" -> Usuario.estadistica.generes[2]++ // Índice 2 para Fantasía
                "terror" -> Usuario.estadistica.generes[3]++ // Índice 3 para Terror
                else -> {

                    Log.w("onMovieClicked", "Género no rastreado encontrado: ${movie.genero}")
                }
            }

            val usuarioApp = application as? Usuario
            if (usuarioApp != null) {
                usuarioApp.saveStats()
                Log.d("onMovieClicked", "Estadísticas guardadas. Géneros: ${Usuario.estadistica.generes}")
            } else {
                Log.e("onMovieClicked", "Error al obtener la instancia de Usuario para guardar estadísticas.")
            }
            val intent = Intent(this, pelicula_engran::class.java)
            intent.putExtra("MOVIE_NOM", movie.nombre)
            intent.putExtra("MOVIE_FOTO", movie.foto)
            intent.putExtra("MOVIE_DESC", movie.descripcion)
            // Posiblemente quieras pasar también el género a la siguiente actividad
            // intent.putExtra("MOVIE_GENERO", movie.genero)
            startActivity(intent)

        } else {
            // Manejar el caso cuando movie es nulo
            Toast.makeText(this, "No se seleccionó ninguna película", Toast.LENGTH_SHORT).show()
        }
    }
}