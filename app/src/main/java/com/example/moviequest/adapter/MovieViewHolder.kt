package com.example.moviequest.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Movie
import com.example.moviequest.R
import com.bumptech.glide.Glide // Importa Glide

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageMovie = view.findViewById<ImageView>(R.id.ivMovie)

    fun render(movieModel: Movie) {
        // Usa Glide para cargar la imagen desde la URL
        Glide.with(itemView.context) // 'itemView.context' es el contexto del ViewHolder
            .load(movieModel.movie_foto) // Carga la URL de la imagen
            .into(imageMovie) // En el ImageView 'imageMovie'
    }
}