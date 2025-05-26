package com.example.practica3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.model.Usuario
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_registro)
        val ListUser = findViewById<ListView>(R.id.lvUsuario)

        loadUser(onResult = { list ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
            ListUser.adapter = adapter
        }, onError = {
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        })



    }

    private fun loadUser(onResult: (List<String>) -> Unit,
                         onError: (Exception) -> Unit ){
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").get().addOnSuccessListener { result ->
            val list = mutableListOf<String>()
            for (document in result) {
                val nombre = document.getString("nombre")
                val correo = document.getString("correo")
                val genero = document.getString("genero")
                val hobbies = document.getString("hobbies")
                val pais = document.getString("pais")

                list.add("$nombre - $correo\n$genero | $hobbies | $pais")
            }
            onResult(list)
        }.addOnFailureListener { onError(it) }




    }

}