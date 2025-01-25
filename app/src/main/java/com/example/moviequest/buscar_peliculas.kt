package com.example.moviequest

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter

class buscar_peliculas : AppCompatActivity(), GenresBottomSheet.GenreSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_peliculas)
        initRecyclerViews()


        findViewById<Button>(R.id.menuIcon).setOnClickListener {
            val bottomSheet = GenresBottomSheet()
            bottomSheet.setGenreSelectionListener(this)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun onGenresSelected(selectedGenres: List<String>) {
        // Manejar los géneros seleccionados
        if (selectedGenres.isNotEmpty()) {
            val genresText = selectedGenres.joinToString(", ")
            Toast.makeText(this, "Géneros seleccionados: $genresText", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No se seleccionaron géneros", Toast.LENGTH_SHORT).show()
        }
    }

    fun initRecyclerViews(){
        //millor valorades
        val rv_mv = findViewById<RecyclerView>(R.id.bestRatedRecycler)
        rv_mv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_mv.adapter = MovieAdapter(MovieProvider.movieList)

        //top 10
        val rv_t10 = findViewById<RecyclerView>(R.id.top10Recycler)
        rv_t10.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_t10.adapter = MovieAdapter(MovieProvider.movieList)

        //Misteri
        val rv_mt = findViewById<RecyclerView>(R.id.misteriRecycler)
        rv_mt.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_mt.adapter = MovieAdapter(MovieProvider.movieList)
    }
}