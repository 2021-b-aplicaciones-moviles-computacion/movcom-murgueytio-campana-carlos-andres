package com.example.aplicacion01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class ACicloVida : AppCompatActivity() {
    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        Log.i("ciclo-vida", "onCreate")
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida) //el R significa recurso
        botonCicloVida.setOnClickListener{
            aumentarTotal()
        }
    }

    fun aumentarTotal(){
        total = total + 1
        val textViewCicloVida = findViewById<TextView>(R.id.tv_ciclo_vida)
        textViewCicloVida.text = total.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) { //En esta funcion guardamos el total
        outState.run{
            //Guardar las variables primitivas
            putInt("totalGuardado", total)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) { //En esta funcion recuperamos ese total guardado
        super.onRestoreInstanceState(savedInstanceState)
        val totalRecuperado:Int? = savedInstanceState.getInt("totalGuardado")
        if(totalRecuperado!=null){
            this.total = totalRecuperado
            val txtCicloVida = findViewById<TextView>(R.id.tv_ciclo_vida)
            txtCicloVida.text = total.toString()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ciclo-vida", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("ciclo-vida", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ciclo-vida", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ciclo-vida", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ciclo-vida", "onDestroy")
    }


}