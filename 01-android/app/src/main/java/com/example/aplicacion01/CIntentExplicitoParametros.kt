package com.example.aplicacion01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad",0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("a",0)
        Log.i("intent","Valores: ${nombre} ${apellido} ${edad}")

        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)

        boton
            .setOnClickListener{devolverRespuesta()}
    }

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado", "Andres")
        intentDevolverParametros.putExtra("edadModificado", "23")

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()

    }
}
