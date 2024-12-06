package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
class footer_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout principal donde se encuentra el 'include'
        val view = inflater.inflate(R.layout.fragment_footer_fragment, container, false)

        // Acceder a la ImageView dentro del layout inflado
        val userImage = view.findViewById<ImageView>(R.id.user)

        // Asegúrate de que la imagen está siendo clickeada
        userImage.setOnClickListener {
            Log.d("footer_fragment", "Imagen del usuario clickeada")  // Verificación en Logcat
            Toast.makeText(requireContext(), "Imagen del usuario clickeada", Toast.LENGTH_SHORT).show()

            // Intent para abrir la actividad del usuario
            val intent = Intent(activity, user_activity::class.java)
            startActivity(intent)
        }

        return view
    }
}
