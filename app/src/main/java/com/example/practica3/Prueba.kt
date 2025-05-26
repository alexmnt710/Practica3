package com.example.practica3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source


class Prueba : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)

        FirebaseApp.initializeApp(this)
        val pgCarga = findViewById<ProgressBar>(R.id.pbCarga)


        Inicio()

    }

    private fun Inicio() {
        val tvNum = findViewById<TextView>(R.id.tvNum)

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("usuarios")

        query.count().get()
            .addOnSuccessListener { snapshot ->
                val count = snapshot.count
                Log.d("Prueba", "El total de usuarios es: $count")
                tvNum.text = count.toString()
            }
            .addOnFailureListener { e ->
                Log.e("Prueba", "Error al contar usuarios", e)
            }
    }

}