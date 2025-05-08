package com.example.moviequest

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Register_activity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sortir: Button = findViewById(R.id.sortir)
        sortir.setOnClickListener{
            finish()
            System.exit(0)
        }

        val iniciSessio: Button = findViewById(R.id.iniciSessio)
        iniciSessio.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        //TESTING
        //test nom en blanc
        val name = findViewById<TextInputEditText>(R.id.name)
        viewModel.noBlankSpaces.observe(this) { valid ->
            if (!valid) {
                name.error = "El nom no put estar buit"
            } else {
                name.error = null
            }
        }

        // Cada vez que cambie el texto, avisamos al ViewModel
        name.addTextChangedListener { text ->
            viewModel.onUsernameChanged(text.toString())
        }

        //test data de neixement en blanc
        val data = findViewById<TextInputEditText>(R.id.aniversari)
        viewModel.noBlankSpaces.observe(this) { valid ->
            if (!valid) {
                data.error = "La data de neixement no pot estar en blanc"
            } else {
                data.error = null
            }
        }

        // Cada vez que cambie el texto, avisamos al ViewModel
        data.addTextChangedListener { text ->
            viewModel.onUsernameChanged(text.toString())
        }

        //test email en blanc
        val email = findViewById<TextInputEditText>(R.id.email)
        viewModel.noBlankSpaces.observe(this) { valid ->
            if (!valid) {
                email.error = "El email no pot estar en blanc"
            } else {
                email.error = null
            }
        }

        // Cada vez que cambie el texto, avisamos al ViewModel
        email.addTextChangedListener { text ->
            viewModel.onUsernameChanged(text.toString())
        }


        //test contrasenyes
        val contrasenya1 = findViewById<TextInputEditText>(R.id.contrasenya1)
        val contrasenya2 = findViewById<TextInputEditText>(R.id.contrasenya2)

        viewModel.passwordsMatch.observe(this) { match ->
            if (!match) {
                contrasenya2.error = "Les contrasenyes no coincideixen"
            } else {
                contrasenya2.error = null
            }
        }

        contrasenya1.addTextChangedListener { text ->
            viewModel.onPasswordChanged(text.toString(), contrasenya2.text.toString())
        }

        contrasenya2.addTextChangedListener { text ->
            viewModel.onPasswordChanged(contrasenya1.text.toString(), text.toString())
        }


    }
    private fun showOptionsDialog(id: Long) {
        val options = arrayOf("Editar nom", "Eliminar")

        AlertDialog.Builder(this)
            .setTitle("Opcions")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarNom(id)
                    1 -> eliminarPartie(id)
                }
            }
            .show()
    }

    private fun editarNom(id: Long) {

    }
    private fun eliminarPartie(id: Long) {

    }

}