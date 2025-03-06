package com.example.moviequest;

import android.os.Bundle;
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide

class pelicula_engran : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelicula_engran)

        val movieName = intent.getStringExtra("MOVIE_NOM")
        val moviePhoto = intent.getStringExtra("MOVIE_FOTO")
        val movieDesc = intent.getStringExtra("MOVIE_DESC")

        // Referencias a las vistas de la UI
        val titleTextView = findViewById<TextView>(R.id.tituloPelicula)
        val imageView = findViewById<ImageView>(R.id.imagenPelicula)
        val descView = findViewById<TextView>(R.id.descripcion)

        titleTextView.text = movieName
        descView.text = movieDesc

        if (!moviePhoto.isNullOrEmpty()) {
            Glide.with(this)
                .load(moviePhoto)
                .into(imageView)
        }
    }
}
