package com.example.moviequest.adapter

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Movie
import com.example.moviequest.R
import java.net.URL

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageMovie = view.findViewById<ImageView>(R.id.ivMovie)

    fun render(movieModel: Movie) {
        try {
            imageMovie.setImageResource(movieModel.movie_foto)
        } catch (e: Exception) {
            e.printStackTrace()
            // Set a default image if needed
            // imageMovie.setImageResource(R.drawable.default_placeholder)
        }
    }
}