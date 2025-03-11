package com.example.moviequest;

import android.os.Bundle;
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class partie_engran : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.partie_engran)

        val movieName = intent.getStringExtra("PARTIE_NOM")
        val movieDesc = intent.getStringExtra("PARTIE_DESC")

        val titleTextView = findViewById<TextView>(R.id.textViewNombre)
        val descView = findViewById<TextView>(R.id.textViewDescripcion)

        titleTextView.text = movieName
        descView.text = movieDesc
    }
}