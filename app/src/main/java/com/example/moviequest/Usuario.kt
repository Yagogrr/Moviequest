package com.example.moviequest

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


data class statistics(
    var elementsBorrats: Int = 0,
    var elementsCreats: Int = 0,
    var generes: ArrayList<Int> = arrayListOf<Int>(0, 0, 0, 0)  //[accion, animacion, fantasia, terror]
)

class Usuario : Application() {
    var nom: String? = null
    var id: Int? = null
    var gmail: String?=null

    fun setDatosUsuario(id: Int, nombre: String, gmail:String) {
        this.id = id
        this.nom = nombre
        this.gmail = gmail
    }

    companion object {
        var idDispositiu = ""
        const val nomAplicacio = "MovieQuest"
        const val idAplicacio = "MovieQuest"
        var estadistica = statistics()
    }

    //La variable s'inicialitzarà la primera vegada que s'utilitzi.
    val db: FirebaseFirestore by lazy { Firebase.firestore }

    override fun onCreate() {
        super.onCreate()

        //Es desaconsella utilitzar ids lligats al dispositiu,
        //https://developer.android.com/identity/user-data-ids
        idDispositiu = Settings.Secure.getString(
            getApplicationContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )

        //Obtenim les dades de la base de dades.
        //Guardarem les tirades en la col·lecció Devices.
        //Per cada Device(identificat amb un id), es guardaran les estadístiques.
        val doc = db.collection("Stats").document(idDispositiu)

        //Obtenim el document corresponent al nostre dispositiu
        doc.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    //El nostre dispositiu ja estava registrat
                    val estadisticabbdd = documentSnapshot.toObject<statistics>()
                    Usuario.estadistica = estadisticabbdd!!

                } else {
                    //El nostre dispositiu no estava registrat, i el guardem amb valors per defecte.
                    db.collection("Stats").document(idDispositiu).set(estadistica)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error en caso de fallo al obtener el documento
                Log.i("App_onCreate", "Error al comprobar la existencia del documento: $exception")
            }
    }

    public fun saveStats() {
        db.collection("Stats").document(idDispositiu).set(estadistica)
            .addOnSuccessListener {
                Log.i("saveStats","Dades guardades correctament")
            }
            .addOnFailureListener {
                Log.i("saveStats",it.message.toString())
                throw it
            }
    }
    public fun resetStats(){
        estadistica= statistics()
    }
}
