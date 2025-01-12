package com.example.moviequest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Movie
import com.example.moviequest.R

class MovieAdapter(private val movieList:List<Movie>) : RecyclerView.Adapter<MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.rv_peliculas,parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = movieList.size
}