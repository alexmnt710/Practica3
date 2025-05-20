package com.example.practica3

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val ListUser = findViewById<ListView>(R.id.lvUsuario)

    }

}