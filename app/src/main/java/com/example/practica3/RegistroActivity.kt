package com.example.practica3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.practica3.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val ListUser = findViewById<ListView>(R.id.lvUsuario)
        val db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,"usuarios").build()

        lifecycleScope.launch {
            db.usuarioDao().getUsuarios().collect { usuarios ->
                val lista = usuarios.map {
                    "${it.nombre} - ${it.correo} \n${it.genero} - ${it.hobbies}\n${it.pais}"

                }
                withContext(Dispatchers.Main){
                    val adapter = ArrayAdapter(this@RegistroActivity,android.R.layout.simple_list_item_1,lista)
                    ListUser.adapter = adapter
                }


            }
        }
    }

}