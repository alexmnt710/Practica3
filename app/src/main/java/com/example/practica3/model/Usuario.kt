package com.example.practica3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val nombre: String,
//    val correo: String,
//    val genero: String,
//    val hobbies: String,
//    val pais: String
    val nombre : String = "",
    val correo : String = "",
    val genero: String = "",
    val hobbies : String = "",
    val pais : String = ""

)