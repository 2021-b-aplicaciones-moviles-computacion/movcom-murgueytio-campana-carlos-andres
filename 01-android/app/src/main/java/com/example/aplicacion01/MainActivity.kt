package com.example.aplicacion01

import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCicloVida = findViewById<Button>(R.id.btn_ir_ciclo_vida)
        botonCicloVida.setOnClickListener{
            //val intent = Intent(this,ACicloVida::class.java);
            //startActivity(intent)
            irActividad(ACicloVida::class.java) //Refactor de lo de arriba
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener{
            //val intent = Intent(this,ACicloVida::class.java);
            //startActivity(intent)
            irActividad(BListView::class.java) //Refactor de lo de arriba
        }

        val botonIntent = findViewById<Button>(R.id.btn_intent)
        botonIntent.setOnClickListener{
            irActividad(CIntentExplicitoParametros::class.java)
        }
    }


    fun abrirActividadConParametros(
        clase: Class<*>,
    ){
        val intentExplicito = Intent (this,clase)
        //Solo podemos enviar variables primitivas
        intentExplicito.putExtra("nombre","Carlos")
        intentExplicito.putExtra("apellido","Murgueytio")
        intentExplicito.putExtra("edad","24")

        startActivityForResult(intent, CODIGO_RESPUESTA_INTENT_EXPLICITO) //Esto esta desuso aunque depende de la API

    }


    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this,clase)
        startActivity(intent)

    }


}