package com.example.proyecto_final_movil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvApellido: TextView = findViewById(R.id.tvApellido)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)

        db.collection("postulante").addSnapshotListener { snapshots, e ->

            if (e != null) {
                Log.w("Firebase", "Error al consultar la colección de postulantes")
//                    Snackbar
//                        .make(
//                            findViewById(android.R.id.content),
//                            "Error al consultar",
//                            Snackbar.LENGTH_LONG
//                        ).show()
                return@addSnapshotListener
            }
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED -> {
//                            Snackbar
//                                .make(
//                                    findViewById(android.R.id.content),
//                                    "Consultando la colección",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
                        tvNombre.text = dc.document.data["Nombre"].toString()
                        tvApellido.text = dc.document.data["Apellido"].toString()
                        tvTelefono.text = dc.document.data["Telefono"].toString()
                    }

                    DocumentChange.Type.REMOVED -> {
                        Snackbar
                            .make(
                                findViewById(android.R.id.content),
                                "Eliminando documentos de la colección",
                                Snackbar.LENGTH_LONG
                            ).show()
                    }

                    else -> {
                        Log.w("Firebase", "Error al consultar la colección de postulantes")
                    }
                }
            }
        }
    }
}