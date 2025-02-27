package com.example.moviequest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.Partie
import com.example.moviequest.R

class PartieAdapter(private val partieList:List<Partie>) : RecyclerView.Adapter<PartieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PartieViewHolder(layoutInflater.inflate(R.layout.rv_partie,parent,false))
    }

    override fun onBindViewHolder(holder: PartieViewHolder, position: Int) {
        val item = partieList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = partieList.size
}