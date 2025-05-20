package com.example.practica3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica3.MainActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var btnRegistroActivity: Button
    private lateinit var btnConsulta : Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        //funcion para presionar y redirigir a la vista de registro
        btnRegistroActivity = findViewById(R.id.btnRegistro)
        btnConsulta = findViewById(R.id.btnConsulta)


        btnRegistroActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnConsulta.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}