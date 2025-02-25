package com.example.moviequest.adapter

import android.app.Application

class Usuario : Application() {
    var nombre: String? = null
    var id: Int? = null

    override fun onCreate() {
        super.onCreate()
        // Inicializar variables si es necesario
    }

    fun setDatosUsuario(id: Int, nombre: String) {
        this.id = id
        this.nombre = nombre
    }

    fun cerrarSesion() {
        this.id = null
        this.nombre = null
    }
}
