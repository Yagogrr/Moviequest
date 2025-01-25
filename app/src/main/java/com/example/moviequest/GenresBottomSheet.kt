package com.example.moviequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
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

        val selectedGenres = mutableListOf<String>()

        // Configurar checkboxes
        val checkboxes = listOf(
            view.findViewById<CheckBox>(R.id.checkAction),
            view.findViewById<CheckBox>(R.id.checkComedy),
            view.findViewById<CheckBox>(R.id.checkDrama),
            view.findViewById<CheckBox>(R.id.checkHorror)
        )

        checkboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val genre = checkbox.text.toString()
                if (isChecked) {
                    selectedGenres.add(genre)
                } else {
                    selectedGenres.remove(genre)
                }
            }
        }

        view.findViewById<Button>(R.id.btnDone).setOnClickListener {
            listener?.onGenresSelected(selectedGenres)
            dismiss()
        }

        return view
    }
}