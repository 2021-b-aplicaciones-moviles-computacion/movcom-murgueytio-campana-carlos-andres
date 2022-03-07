package com.example.eamenprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class EditarDirector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_director)

        var editarDirector = intent.getSerializableExtra("director") as Director
        val nombre = findViewById<EditText>(R.id.txt_nombre_editar)
        val nacionalidad =  findViewById<EditText>(R.id.txt_descripcion_editar)
        val isalive =  findViewById<EditText>(R.id.txt_salas_Editar)
        val fecha =  findViewById<EditText>(R.id.txt_fecha_editar)

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val fechaComoCadena = sdf.format(editarDirector.fecha_nacimiento)

        nombre.setText(editarDirector.nombre)
        nacionalidad.setText(editarDirector.nacionalidad)
        isalive.setText(editarDirector.isAlive.toString())
        fecha.setText(fechaComoCadena)

        val botoneditar = findViewById<Button>(R.id.btn_editar)


        botoneditar.setOnClickListener{
            if(nombre.text.isNotBlank() && nacionalidad.text.isNotBlank() && isalive.text.isNotBlank() && fecha.text.isNotBlank()){
                if(validar_sala(isalive.text.toString()) == true && validar_fecha(fecha.text.toString()) == true ){
                    val nuevoRestaurante = hashMapOf<String, Any>(
                        "nombre" to nombre.text.toString(),
                        "nacionalidad" to nacionalidad.text.toString(),
                        "isalive" to isalive.text.toString(),
                        "fecha" to fecha.text.toString(),
                    )
                    val db = Firebase.firestore
                    val referencia = db.collection("director").document("${nombre.text}-${nacionalidad.text}")
                    referencia.set(nuevoRestaurante).addOnSuccessListener {
                        nombre.text.clear()
                        nacionalidad.text.clear()
                        isalive.text.clear()
                        fecha.text.clear()

                    }.addOnFailureListener {

                    }

                    abrirActividad(EDirector::class.java)

                }else{
                    Toast.makeText(this, "Llene los campos correctamente", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Llene los Campos", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun validar_nombre(nombre:String): Boolean{
        val validar = Regex("^[a-zA-Z]+\\ [a-zA-Z]*$")

        return validar.matches(nombre)

    }



    fun validar_nacionalidad(nacionalidad:String):Boolean{
        val validar = Regex("^[a-zA-Z]+\\ [a-zA-Z]*$")
        return validar.matches(nacionalidad)
    }

    fun validar_sala(sala: String):Boolean{
        val validar = Regex("^[0-9]{1,2}+$")
        //return validar.matches(sala)
        return true
    }

    fun validar_estrella(estrella: String):Boolean{
        val validar = Regex("[0-9]+\\.[0-9]+")
        return validar.matches(estrella)
    }

    fun validar_fecha(fecha:String):Boolean{
        val validar = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])\\/(19|20)\\d\\d")
        //return validar.matches(fecha)
        return true
    }

    fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)

    }
}