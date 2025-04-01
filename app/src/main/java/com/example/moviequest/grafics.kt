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


        UpdateBarGraph()
        UpdatePieGraph()

    }



    private fun UpdatePieGraph() {
        val entries = listOf(
            PieEntry(MainApp.estadistica.numdobles.toFloat() , "Dobles"), // Valor 70%
            PieEntry((MainApp.estadistica.tirades-MainApp.estadistica.numdobles).toFloat(), "No Dobles")  // Valor 30%
        )
        val pieDataSet = PieDataSet(entries, "Distribució")
        pieDataSet.colors = listOf(Color.rgb(135, 206, 250), Color.rgb(255, 182, 193)) // Blau clar i rosa clar
        pieDataSet.valueTextColor = Color.BLACK // Color del text
        pieDataSet.valueTextSize = 16f // Mida del text

        // Afegeix el DataSet al PieData
        binding.piechartDobles.apply {
            data= PieData(pieDataSet)
            description.isEnabled = false // Desactiva la descripció
            isDrawHoleEnabled = true // Mostra el forat al centre
            holeRadius = 40f // Mida del forat
            setHoleColor(Color.WHITE) // Color del forat
            animateY(1000) // Animació en Y
            setEntryLabelColor(Color.BLACK) // Color de les etiquetes
            setEntryLabelTextSize(14f) // Mida del text de les etiquetes

            // Actualitza el gràfic
            invalidate()
        }
    }


    private fun UpdateBarGraph(){
        val entries = ArrayList<BarEntry>()
        for (i in MainApp.estadistica.daus.indices) {
            entries.add(BarEntry((i+1).toFloat(), MainApp.estadistica.daus[i].toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Resultats Daus")

        val pastelColors = listOf(
            Color.rgb(255, 204, 204), // Rosa clar
            Color.rgb(204, 229, 255), // Blau clar
            Color.rgb(204, 255, 204), // Verd clar
            Color.rgb(255, 255, 204), // Groc clar
            Color.rgb(255, 229, 204), // Taronja clar
            Color.rgb(229, 204, 255)  // Lila clar
        )
        barDataSet.colors=pastelColors
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f
        barDataSet.valueFormatter= DefaultValueFormatter(0)

        binding.barchartDaus.apply {
            data= BarData(barDataSet)
            //setDrawGridBackground(false)

            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)
            axisRight.setDrawLabels(false)
            description.text="Freqüència daus"
            description.isEnabled = true // Activa la descripció
            setFitBars(true) // Ajusta les barres al gràfic
            animateY(1000) // Animació en Y
            invalidate()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_estadistica, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.menuGuardar)
        {
            //Guardar

            try {
                (application as MainApp).saveStats()
                this.statsSaved=true
                Toast.makeText(this, "Estadística guardada correctament", Toast.LENGTH_SHORT).show()
            }catch (e: Exception) {
                Toast.makeText(this, "Error:"+e.message, Toast.LENGTH_SHORT).show()
            }

            //Mostrar info usuari
            return true
        }else return super.onOptionsItemSelected(item)
    }

    private fun resetStats(view: View?) {
        (application as MainApp).resetStats()
        this.statsSaved=false

        UpdateBarGraph()
        UpdatePieGraph()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menuGuardar)?.setEnabled(!this.statsSaved)
        return super.onPrepareOptionsMenu(menu)
    }

}