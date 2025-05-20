package com.example.practica3

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter

import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Room
import com.example.practica3.R
import com.example.practica3.database.AppDatabase
import com.example.practica3.model.Usuario
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    //declarar variables para cada componente de la interfaz grafica
    private lateinit var etNombre: TextInputLayout
    private lateinit var etCorreo: TextInputLayout
    private lateinit var rgGenero: RadioGroup //grupo de botones de genero
    private lateinit var chkLeer: CheckBox //checkbox de leer
    private lateinit var chkJugar: CheckBox //checkbox de jugar
    private lateinit var chkDormir: CheckBox //checkbox de dormir
    private lateinit var spnPaises: Spinner //spinner de paises
    private lateinit var btnRegistrar: Button //boton de enviar
    private lateinit var cardResultado: CardView //cardview de paises
    private lateinit var tvResultado: TextView //textview de resultado
    private lateinit var btnLimpiar: Button //boton de limpiar
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        //inicializarDb()
        //Asociamos cada variable con su elemento de la vista
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        rgGenero = findViewById(R.id.rgGenero)
        chkLeer = findViewById(R.id.chkLeer)
        chkJugar = findViewById(R.id.chkJugar)
        chkDormir = findViewById(R.id.chkDormir)
        spnPaises = findViewById(R.id.spnPaises)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        cardResultado = findViewById(R.id.cardResultado)
        tvResultado = findViewById(R.id.tvResultado)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        //Llenar spinner con paises
        val paises = arrayOf("Ecuador", "Colombia", "Peru", "Argentina","Brazil")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spnPaises.adapter = adapter

        btnRegistrar.setOnClickListener{
            //mostrarDatos();
            guardarUsuario();
        }


    }


    private fun guardarUsuario(){
        etNombre.error = null
        etCorreo.error = null

        val nombre = etNombre.editText?.text.toString().trim()
        val correo = etCorreo.editText?.text.toString().trim()

        // Validar nombre
        if (nombre.isEmpty()) {
            etNombre.error = "El nombre es obligatorio"
            return
        }
        if (nombre.length < 8 || nombre.any { it.isDigit() }) {
            etNombre.error = "Debe tener al menos 8 caracteres y no contener números"
            return
        }

        // Validar correo
        if (correo.isEmpty()) {
            etCorreo.error = "El correo es obligatorio"
            return
        }
        if (!correo.contains("@") || !correo.contains(".com")) {
            etCorreo.error = "Correo inválido"
            return
        }
        val generoId = rgGenero.checkedRadioButtonId
        if (generoId == -1) {
            Toast.makeText(this, "Seleccione un género", Toast.LENGTH_SHORT).show()
            return
        }

        val generoNombre = findViewById<RadioButton>(generoId).text.toString()

        // Hobbies seleccionados
        val hobbies = mutableListOf<String>().apply {
            if (chkLeer.isChecked) add("Leer")
            if (chkJugar.isChecked) add("Jugar")
            if (chkDormir.isChecked) add("Dormir")
        }.joinToString { "," }

        val pais = spnPaises.selectedItem.toString()

        val usuario = Usuario(
            nombre = nombre,
            correo = correo,
            genero = generoNombre,
            hobbies = hobbies,
            pais = pais
        )

        //registrarUser(usuario)
        saveUser(usuario)

    }

    private fun saveUser(usuario: Usuario){
        val db = FirebaseFirestore.getInstance()

        db.collection("usuarios")
            .add(usuario).addOnSuccessListener{
                Toast.makeText(this,"Usuario creado",Toast.LENGTH_SHORT).show()
                clear()

                android.os.Handler(mainLooper).postDelayed({
                    irHome()
                },500)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }


    private fun inicializarDb(){
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "usuarios"
        ).build()
    }

    private fun registrarUser(usuario: Usuario){
        lifecycleScope.launch(Dispatchers.IO){
            db.usuarioDao().insertar(usuario)
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                clear()
                irHome()
            }
        }
    }

    private fun irHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun clear() {
        etNombre.editText?.text?.clear()
        etCorreo.editText?.text?.clear()
        rgGenero.clearCheck()
        chkLeer.isChecked = false
        chkJugar.isChecked = false
        chkDormir.isChecked = false
        spnPaises.setSelection(0)
        cardResultado.visibility = CardView.GONE
        btnLimpiar.visibility = Button.GONE
    }


}