package com.example.eamenprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearDirector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_director)
        val nombre = findViewById<EditText>(R.id.txt_nombre_crear)
        val nacionalidad = findViewById<EditText>(R.id.txt_nacionalidad)
        val isAlive = findViewById<EditText>(R.id.txt_isalive)
        val fecha = findViewById<EditText>(R.id.txt_fecha_editar)


        val crearDirector = findViewById<Button>(R.id.btn_crear2)
        crearDirector.setOnClickListener {
            if(nombre.text.isNotBlank() && nacionalidad.text.isNotBlank() && isAlive.text.isNotBlank()){
                if(validar_fecha(fecha.text.toString())){
                    crearDirector()
                    abrirActividad(EDirector::class.java)
                }else{
                    Toast.makeText(this,"Ingrese los campos correctamente", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Llene los Campos", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun crearDirector() {

        val nombre = findViewById<EditText>(R.id.txt_nombre_crear)
        val nacionalidad = findViewById<EditText>(R.id.txt_nacionalidad)
        val isAlive = findViewById<EditText>(R.id.txt_isalive)
        val fecha = findViewById<EditText>(R.id.txt_fecha_editar)

        val nuevoRestaurante = hashMapOf<String, Any>(
            "nombre" to nombre.text.toString(),
            "nacionalidad" to nacionalidad.text.toString(),
            "isAlive" to isAlive.text.toString(),
            "fecha" to fecha,

            )
        val db = Firebase.firestore
        val referencia = db.collection("director").document("${nombre.text}-${nacionalidad.text}")
        referencia.set(nuevoRestaurante).addOnSuccessListener {
            nombre.text.clear()
            nacionalidad.text.clear()
            isAlive.text.clear()
            fecha.text.clear()

        }.addOnFailureListener {

        }

    }


    fun validar_fecha(fecha:String):Boolean{
        val validar = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])\\/(19|20)\\d\\d")
        return validar.matches(fecha)
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }


}