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

        // Establecer listeners para cada botón de género
        val buttons = listOf(btnTerror, btnComedia, btnAccio, btnDibuixos)
        buttons.forEach { button ->
            button.setOnClickListener {
                // Navegar a MainActivity con el género seleccionado
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("SELECTED_GENRE", button.text.toString())
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