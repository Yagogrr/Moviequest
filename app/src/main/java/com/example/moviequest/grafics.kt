package com.example.moviequest

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moviequest.databinding.ActivityGraficsBinding

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter


class grafics : AppCompatActivity() {
    private lateinit var binding: ActivityGraficsBinding
    private var statsSaved:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityGraficsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title =Usuario.nomAplicacio
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }



}