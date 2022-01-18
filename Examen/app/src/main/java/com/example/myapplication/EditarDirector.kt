package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat

class EditarDirector : AppCompatActivity() {
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 403

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_director)
        BaseDatos.Base= Tablas(this)
        val Director_pelicula = intent.getParcelableExtra<EDirector>("Director")
        Log.i("bdd","Editar ${Director_pelicula}")
        val nombre = findViewById<EditText>(R.id.txt_nombre_editar)
        val nacionalidad =  findViewById<EditText>(R.id.txt_nacionalidad_editar)
        val isAlive =  findViewById<EditText>(R.id.txt_IsAlive_Editar)
        val fecha =  findViewById<EditText>(R.id.txt_fecha_editar)
        val id:Int = Director_pelicula!!.id_director

        nombre.setText(Director_pelicula?.nombre)
        nacionalidad.setText(Director_pelicula?.nacionalidad)
        isAlive.setText(Director_pelicula?.isAlive.toString())
        fecha.setText(SimpleDateFormat("dd/MM/yyyy").format(Director_pelicula?.fecha_nac))


        val botoneditar = findViewById<Button>(R.id.btn_editar)
        botoneditar.setOnClickListener {
            if (nombre.text.isNotBlank() && nacionalidad.text.isNotBlank() && isAlive.text.isNotBlank() &&
                fecha.text.isNotBlank()
            ) {
                if (BaseDatos.Base != null) {
                    val Director = BaseDatos.Base!!.editarDirector(
                        nombre.text.toString(),
                        SimpleDateFormat("dd/MM/yyyy").parse(fecha.text.toString()),
                        nacionalidad.text.toString(),
                        isAlive.text.toString(),
                        id
                    )
                    if (Director == true) {
                        Toast.makeText(this, "Actualizado con exito", Toast.LENGTH_SHORT)
                            .show()

                        abrirActividadConParametros(Director::class.java, Director_pelicula!!)
                    } else {
                        Toast.makeText(this, "error al actualizar Director", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                Toast.makeText(this, "Llene los Campos", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito) }


    fun abrirActividadConParametros(clase: Class<*>, director: EDirector, ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("Director",director)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }
}