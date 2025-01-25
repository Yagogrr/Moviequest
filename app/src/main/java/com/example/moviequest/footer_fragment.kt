package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

class footer_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_footer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.house -> {
                    true
                }

                R.id.search -> {
                    startActivity(Intent(requireActivity(), buscar_peliculas::class.java))
                    true
                }

                R.id.user -> {
                    startActivity(Intent(requireActivity(), user_activity::class.java))
                    true
                }

                R.id.controller -> {
                    // Lógica para controller
                    true
                }

                R.id.party -> {
                    // Lógica para party
                    true
                }

                else -> false
            }
        }
    }
}