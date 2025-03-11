package com.example.moviequest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Partie
import com.example.moviequest.R
import com.example.moviequest.partie_engran

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

        // Configurar el listener para el clic largo en el ítem (para eliminar)
        holder.itemView.setOnLongClickListener {
            onPartieLongClicked(item) // Invocar la función pasada como parámetro
            true // Retornar true para indicar que el evento fue consumido
        }

        // Agregar listener para el clic normal (para abrir partie_engran)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, partie_engran::class.java).apply {
                putExtra("PARTIE_NOM", item.titulo)
                putExtra("PARTIE_DESC", item.descripcion)
            }
            context.startActivity(intent)
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