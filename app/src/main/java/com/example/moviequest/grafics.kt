package com.example.moviequest

import android.graphics.Color
import android.os.Bundle
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
import com.github.mikephil.charting.formatter.ValueFormatter


class grafics : AppCompatActivity() {
    private lateinit var binding: ActivityGraficsBinding
    private var statsSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGraficsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = Usuario.nomAplicacio
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        UpdateBarGraph()
        UpdatePieGraph()
    }

    private fun UpdatePieGraph() {
        val entries = listOf(
            PieEntry(Usuario.estadistica.elementsCreats.toFloat(), "Elements creats"), // Valor 70%
            PieEntry(Usuario.estadistica.elementsBorrats.toFloat(), "Elements eliminats")  // Valor 30%
        )
        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.colors = listOf(Color.rgb(135, 206, 250), Color.rgb(255, 182, 193)) // Blau clar i rosa clar
        pieDataSet.valueTextColor = Color.BLACK // Color del text
        pieDataSet.valueTextSize = 16f // Mida del text

        // Afegeix el DataSet al PieData
        binding.grafic1.apply {
            data = PieData(pieDataSet)
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

    private fun UpdateBarGraph() {
        val entries = ArrayList<BarEntry>()
        val textos = listOf("accio", "animacio", "fantasia", "terror")
        for (i in Usuario.estadistica.generes.indices) {
            entries.add(BarEntry(i.toFloat(), Usuario.estadistica.generes[i].toFloat())) // Usa i para el índice
        }

        val barDataSet = BarDataSet(entries, "Generes")
        val pastelColors = listOf(
            Color.rgb(255, 204, 204), // Rosa clar
            Color.rgb(204, 229, 255), // Blau clar
            Color.rgb(204, 255, 204), // Verd clar
            Color.rgb(229, 204, 255)  // Lila clar
        )
        barDataSet.colors = pastelColors
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f
        binding.grafic2.xAxis.apply {
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (index in textos.indices) textos[index] else ""
                }
            }
            setDrawLabels(true)
            granularity = 1f // Asegura que solo haya un valor por cada barra
            labelCount = textos.size // Establece el número de etiquetas
            position = XAxis.XAxisPosition.BOTTOM // Coloca las etiquetas en la parte inferior
        }
        binding.grafic2.apply {
            data = BarData(barDataSet)
            xAxis.setDrawGridLines(false) // Desactivar las líneas del eje X
            axisLeft.setDrawGridLines(false) // Desactivar las líneas del eje Y
            axisRight.setDrawAxisLine(false) // Desactivar la línea del eje Y derecho
            axisRight.setDrawGridLines(false) // Desactivar las líneas del eje Y derecho
            axisRight.setDrawLabels(false) // Desactivar las etiquetas del eje Y derecho
            description.isEnabled = false // Desactivar la descripción
            setFitBars(true) // Ajusta las barras al gráfico
            animateY(1000) // Animación en Y
            invalidate() // Actualizar el gráfico
        }
    }


}
