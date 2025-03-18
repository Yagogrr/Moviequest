package com.example.moviequest.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Partie
import com.example.moviequest.R


class PartieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvTituloPartie = view.findViewById<TextView>(R.id.tvTituloPartie)


    fun render(partieModel: Partie) {
        tvTituloPartie.text = partieModel.titulo
    }
}