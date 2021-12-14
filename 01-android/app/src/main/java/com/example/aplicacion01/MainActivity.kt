package com.example.aplicacion01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
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
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this,clase)
        startActivity(intent)

    }


}