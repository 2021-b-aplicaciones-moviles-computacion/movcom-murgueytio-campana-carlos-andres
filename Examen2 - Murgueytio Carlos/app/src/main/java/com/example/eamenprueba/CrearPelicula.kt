package com.example.eamenprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CrearPelicula : AppCompatActivity() {

    companion object{
        var nombre = ""
    }
    var arregloUbicaciones = ArrayList<Ubicación>()
    var ubicacionSeleccionado: Ubicación? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)


        var nombre = findViewById<EditText>(R.id.txt_crear_nombre)
        val director = findViewById<EditText>(R.id.txt_crear_director)
        val taquilla = findViewById<EditText>(R.id.txt_crear_taquilla)
        val cartelera = findViewById<EditText>(R.id.txt_crear_cartelera)
        //var altitud = findViewById<EditText>(R.id.txtaltitud)
        //var latitud = findViewById<EditText>(R.id.txtlatitud)
        var documentoUbicacion: (MutableList<DocumentSnapshot>)

        val db = Firebase.firestore

        var referencia = db.collection("ubicacion")
            .get()
            .addOnSuccessListener {
                documentoUbicacion = it.documents
                documentoUbicacion.forEach { iteracion ->
                    arregloUbicaciones.add(
                        Ubicación(iteracion.get("nombre").toString(),
                    iteracion.get("latitud").toString().toDouble(),
                        iteracion.get("longitud").toString().toDouble())
                    )

                }

                var spinnerRestaurantes = findViewById<Spinner>(R.id.sp_producto)
                val adaptadorRestaurantes = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    arregloUbicaciones
                )
                spinnerRestaurantes.adapter = adaptadorRestaurantes
                spinnerRestaurantes.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        ubicacionSeleccionado = arregloUbicaciones[position]


                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }


                }
            }





      //  val spinner = findViewById<Spinner>(R.id.spinner_pelicula)


        val botonCrear = findViewById<Button>(R.id.btn_Crear_pelicula_3)
        botonCrear.setOnClickListener {
            if(nombre.text.isNotBlank() && director.text.isNotBlank() && taquilla.text.isNotBlank() &&
                cartelera.text.isNotBlank()){
                if(validar_taquilla(taquilla.text.toString()) == true &&
                    validar_cartelera(cartelera.text.toString()) == true){
                    crearpelicula()
                    abrirActividad(EPelicula::class.java)
                }else{
                    Toast.makeText(this, "Llene correctamente los campos", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Llene los campos", Toast.LENGTH_SHORT).show()
            }


        }

    }

    fun crearpelicula() {

        val id = EDirector.id_a

        val nombre = findViewById<EditText>(R.id.txt_crear_nombre)
        val director = findViewById<EditText>(R.id.txt_crear_director)
        val taquilla = findViewById<EditText>(R.id.txt_crear_taquilla)
        val cartelera = findViewById<EditText>(R.id.txt_crear_cartelera)
        //val altitud = findViewById<EditText>(R.id.txtaltitud)
        //val latitud = findViewById<EditText>(R.id.txtlatitud)

        val nuevoRestaurante = hashMapOf<String, Any>(
            "nombre" to nombre.text.toString(),
            "director" to director.text.toString(),
            "taquilla" to taquilla.text.toString(),
            "cartelera" to cartelera.text.toString(),
            "altitud" to ubicacionSeleccionado!!.longitud.toString(),
            "latitud" to ubicacionSeleccionado!!.latitud.toString(),

        )
        val db = Firebase.firestore
        val referencia = db.collection("director ").document(id).collection("pelicula")
            .document("${nombre.text}-${director.text}")
        referencia.set(nuevoRestaurante).addOnSuccessListener {
            nombre.text.clear()
            director.text.clear()
            taquilla.text.clear()
            cartelera.text.clear()
            //altitud.text.clear()
            //latitud.text.clear()

        }.addOnFailureListener {

        }

    }

    fun validar_nombre(nombre: String): Boolean {
        val validar = Regex("^[a-zA-Z0-9]+\\ [a-zA-Z0-9]*$")

        return validar.matches(nombre)

    }


    fun validar_direccion(direccion: String): Boolean {
        val validar = Regex("^[a-zA-Z]+$")
        return validar.matches(direccion)
    }


    fun validar_taquilla(estrella: String): Boolean {
        val validar = Regex("^[0-9]+\\.[0-9]+$")
        return validar.matches(estrella)
    }

    fun validar_cartelera(cartelera: String): Boolean {
        if (cartelera == "true") {
            return true
        }
        if (cartelera == "false") {
            return true
        } else {
            return false
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