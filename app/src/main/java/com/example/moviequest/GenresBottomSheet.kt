package com.example.moviequest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GenresBottomSheet : BottomSheetDialogFragment() {

    interface GenreSelectionListener {
        fun onGenresSelected(selectedGenres: List<String>)
    }

    private var listener: GenreSelectionListener? = null

    fun setGenreSelectionListener(listener: GenreSelectionListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_genres, container, false)

        // Configurar botones de géneros
        val btnTerror = view.findViewById<Button>(R.id.btnTerror)
        val btnComedia = view.findViewById<Button>(R.id.btnComedia)
        val btnAccio = view.findViewById<Button>(R.id.btnAccio)
        val btnDibuixos = view.findViewById<Button>(R.id.btnDibuixos)

        // Mapeo de los botones a los valores de género para la API
        val genreMapping = mapOf(
            btnTerror to "terror",
            btnComedia to "fantasia",
            btnAccio to "accion",
            btnDibuixos to "animacion"
        )

        // Establecer listeners para cada botón de género
        genreMapping.forEach { (button, apiGenre) ->
            button.setOnClickListener {
                // Navegar a Filtres con el género seleccionado
                val intent = Intent(requireContext(), Filtres::class.java)
                intent.putExtra("SELECTED_GENRE", apiGenre)
                intent.putExtra("GENRE_DISPLAY_NAME", button.text.toString())
                startActivity(intent)
                dismiss() // Cerrar el bottom sheet
            }
        }

        // Botón para cerrar el bottom sheet
        view.findViewById<Button>(R.id.btnDone).setOnClickListener {
            dismiss()
        }

        return view
    }
}