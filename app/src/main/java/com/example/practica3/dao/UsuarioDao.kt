package com.example.practica3.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.practica3.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao{
    @Insert
    suspend fun insertar(usuario: Usuario)
    @Query("SELECT * FROM usuarios")
    fun getUsuarios(): Flow<List<Usuario>>

}