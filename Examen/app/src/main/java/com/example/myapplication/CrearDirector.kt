package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CrearDirector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_director)
        BaseDatos.Base= Tablas(this)
        val nombre = findViewById<EditText>(R.id.txt_nombre_crear)
        val nacionalidad =  findViewById<EditText>(R.id.txt_nacionalidad_crear)
        val isAlive =  findViewById<EditText>(R.id.txt_IsAlive_Crear)
        val fecha  =  findViewById<EditText>(R.id.txt_fecha_Crear)
        val botoncrear2 = findViewById<Button>(R.id.btn_crear2)
        botoncrear2.setOnClickListener{
            if (nombre.text.isNotBlank() && nacionalidad.text.isNotBlank() && isAlive.text.isNotBlank() && fecha.text.isNotBlank()
            ){
                if (BaseDatos.Base != null) {
                    val Director = BaseDatos.Base!!.crearDirector(
                        nombre.text.toString(),
                        fecha.text.toString(),
                        nacionalidad.text.toString(),
                        isAlive.text.toString()
                    )
                    if (Director == true) {
                        Toast.makeText(
                            this,
                            "Director creado con exito",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        abrirActividad(Director::class.java)
                    } else {
                        Toast.makeText(this, "Error al crear", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }else{
                Toast.makeText(this,"Llene los Campos", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }
}


