package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [footer_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class footer_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_footer_fragment, container, false)

        // Referencias a los ImageView
        val controller: ImageView = view.findViewById(R.id.controller)
        val party: ImageView = view.findViewById(R.id.party)
        val house: ImageView = view.findViewById(R.id.house)
        val search: ImageView = view.findViewById(R.id.search)
        val user: ImageView = view.findViewById(R.id.user)


        search.setOnClickListener {
            // Acción para la búsqueda
            val intent = Intent(activity, footer_fragment::class.java)
            startActivity(intent)
        }

        user.setOnClickListener {
            // Acción para el usuario
            val intent = Intent(activity, user_activity::class.java)
            startActivity(intent)
        }

        return view
    }
}
