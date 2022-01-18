package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditarPelicula : AppCompatActivity() {

    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 404
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pelicula)
        BaseDatos.Base = Tablas(this)
        val pel = intent.getParcelableExtra<EPelicula>("pelicula")
        val id = pel!!.id_pelicula
        val nombre = findViewById<EditText>(R.id.txt_nombre_editar_pelicula)
        val valoracion = findViewById<EditText>(R.id.txt_valoracion_editar_pelicula)
        val taquilla = findViewById<EditText>(R.id.txt_presupuesto_pelicula)
        val cartelera = findViewById<EditText>(R.id.txt_editar_cartelera)
        val botonEditar = findViewById<Button>(R.id.btn_editar_pelicula)

        botonEditar.setOnClickListener {
            if (BaseDatos.Base != null) {
                if (nombre.text.toString().isNotBlank() && valoracion.text.toString().isNotBlank() && taquilla.text.toString().isNotBlank() &&
                    cartelera.text.toString().isNotBlank()
                ) {
                    val pelicula = BaseDatos.Base!!.editarPelicula(
                        nombre.text.toString(),
                        valoracion.text.toString().toDouble(),
                        taquilla.text.toString().toDouble(),
                        cartelera.text.toString().toBoolean(), id
                    )

                    if (pelicula == true) {
                        Toast.makeText(this, "Pelicula Editada con exito", Toast.LENGTH_SHORT)
                            .show()
                        abrirActividad(Pelicula::class.java)


                    } else {
                        Toast.makeText(
                            this,
                            "No se pudo editar la pelicula",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(this, "Llene los Campos", Toast.LENGTH_SHORT).show()
                }
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

    fun abrirActividadConParametros(clase: Class<*>, director: EDirector, ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("director",director)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }
}
