package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class footer_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_footer_fragment, container, false)

        // ImageView references
        val controller: ImageView = view.findViewById(R.id.controller)
        val party: ImageView = view.findViewById(R.id.party)
        val house: ImageView = view.findViewById(R.id.house)
        val search: ImageView = view.findViewById(R.id.search)
        val user: ImageView = view.findViewById(R.id.user)

        // Intent to buscar_peliculas
        search.setOnClickListener {
            val intent = Intent(requireActivity(), buscar_peliculas::class.java)
            startActivity(intent)
        }

        // Intent to user_activity
        user.setOnClickListener {
            val intent = Intent(requireActivity(), user_activity::class.java)
            startActivity(intent)
        }

        return view
    }
}
