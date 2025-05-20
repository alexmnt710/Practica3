package com.example.practica3.database

import androidx.room.Database
import com.example.practica3.dao.UsuarioDao
import com.example.practica3.model.Usuario

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}