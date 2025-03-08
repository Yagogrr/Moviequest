package com.example.moviequest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Partie
import com.example.moviequest.R

class PartieAdapter(
    private val partieList: List<Partie>,
    private val onPartieLongClicked: (Partie) -> Unit
) : RecyclerView.Adapter<PartieAdapter.PartieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PartieViewHolder(layoutInflater.inflate(R.layout.rv_partie, parent, false))
    }

    override fun onBindViewHolder(holder: PartieViewHolder, position: Int) {
        val item = partieList[position]
        holder.render(item)

        // Configurar el listener para el clic largo en el ítem
        holder.itemView.setOnLongClickListener {
            onPartieLongClicked(item) // Invocar la función pasada como parámetro
            true // Retornar true para indicar que el evento fue consumido
        }
    }

    override fun getItemCount(): Int = partieList.size

    class PartieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titrePartie = view.findViewById<TextView>(R.id.tvTituloPartie)

        fun render(partie: Partie) {
            titrePartie.text = partie.titulo
        }
    }
}
