package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CrearPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)
        BaseDatos.Base = Tablas(this)
        val a = intent.getParcelableExtra<EDirector>("Director")
        val nombre = findViewById<EditText>(R.id.txt_crear_nombre)
        val valoracion = findViewById<EditText>(R.id.txt_crear_valoracion)
        val presupuesto = findViewById<EditText>(R.id.txt_crear_presupuesto)
        val cartelera = findViewById<EditText>(R.id.txt_crear_cartelera)
        val id:Int = Director.id_a
        val botoneditar = findViewById<Button>(R.id.btn_Crear_pelicula_3)
        botoneditar.setOnClickListener {
            if (nombre.text.isNotBlank() && valoracion.text.isNotBlank() && presupuesto.text.isNotBlank() &&
                cartelera.text.isNotBlank()
            ) {
                if (BaseDatos.Base != null) {
                    val Director = BaseDatos.Base!!.crearPelicula(
                        nombre.text.toString(),
                        valoracion.text.toString().toDouble(),
                        presupuesto.text.toString().toDouble(),
                        cartelera.text.toString().toBoolean(),
                        id
                    )
                    if (Director == true) {
                        Toast.makeText(this, "Creado con exito", Toast.LENGTH_SHORT)
                            .show()

                        abrirActividad(Pelicula::class.java)
                    } else {
                        Toast.makeText(this, "error al crear", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                Toast.makeText(this, "Llene los Campos", Toast.LENGTH_SHORT).show()
            }
        }
    }



    fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }
}

