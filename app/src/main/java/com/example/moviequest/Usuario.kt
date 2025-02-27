package com.example.moviequest

import android.app.Application

class Usuario : Application() {
    var nom: String? = null
    var id: Int? = null
    var gmail: String?=null

    override fun onCreate() {
        super.onCreate()
    }

    fun setDatosUsuario(id: Int, nombre: String, gmail:String) {
        this.id = id
        this.nom = nombre
        this.gmail = gmail
    }

    fun cerrarSesion() {
        this.id = null
        this.nom = null
        this.gmail = null
    }
}
