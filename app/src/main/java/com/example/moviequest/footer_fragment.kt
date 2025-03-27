package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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


        setSelectedNavItem(bottomNav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.house -> {
                    true
                }

                R.id.search -> {
                    if (activity !is buscar_peliculas) {
                        startActivity(Intent(requireActivity(), buscar_peliculas::class.java))
                    }
                    true
                }

                R.id.user -> {
                    if (activity !is user_activity) {
                        startActivity(Intent(requireActivity(), user_activity::class.java))
                    }
                    true
                }

                R.id.controller -> {
                    if (activity !is Partiesglobal) {
                        startActivity(Intent(requireActivity(), grafics::class.java))
                    }
                    true
                }

                R.id.party -> {
                    if (activity !is Partiesglobal) {
                        startActivity(Intent(requireActivity(), Partiesglobal::class.java))
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun setSelectedNavItem(bottomNav: BottomNavigationView) {

        when (activity?.javaClass) {
            buscar_peliculas::class.java -> bottomNav.selectedItemId = R.id.search
            user_activity::class.java -> bottomNav.selectedItemId = R.id.user
            Partiesglobal::class.java -> bottomNav.selectedItemId = R.id.party
            grafics::class.java -> bottomNav.selectedItemId = R.id.controller

        }
    }
}