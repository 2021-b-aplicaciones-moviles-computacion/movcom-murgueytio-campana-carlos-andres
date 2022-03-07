package com.example.eamenprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarPelicula : AppCompatActivity() {

    var arregloUbicaciones = ArrayList<Ubicación>()
    var ubicacionSeleccionado: Ubicación? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pelicula)

        var editarPelicula = intent.getSerializableExtra("pelicula") as Pelicula
        val botonEditar = findViewById<Button>(R.id.btn_editar_pelicula)

        val nombre = findViewById<EditText>(R.id.txt_nombre_editar_pelicula)
        val director = findViewById<EditText>(R.id.txt_director_editar_pelicula)
        val taquilla = findViewById<EditText>(R.id.txt_editar_taquilla_pelicula)
        val cartelera = findViewById<EditText>(R.id.txt_editar_cartelera)
       // val altitud = findViewById<EditText>(R.id.editar_tct_altitud)
        //val latitud = findViewById<EditText>(R.id.txt_latitud_editar)

        var documentoUbicacion: (MutableList<DocumentSnapshot>)

        val db = Firebase.firestore

        var referencia = db.collection("ubicacion")
            .get()
            .addOnSuccessListener {
                documentoUbicacion = it.documents
                documentoUbicacion.forEach { iteracion ->
                    arregloUbicaciones.add(
                        Ubicación(
                            iteracion.get("nombre").toString(),
                            iteracion.get("latitud").toString().toDouble(),
                            iteracion.get("longitud").toString().toDouble()
                        )
                    )

                }
                var spinnerRestaurantes = findViewById<Spinner>(R.id.spinner_pelicula)
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








        val aux_taquilla = editarPelicula!!.taquilla.toString()
        val aux_cartelera = editarPelicula!!.cartelera.toString()
        nombre.setText(editarPelicula?.nombre)
        director.setText(editarPelicula?.director)
        taquilla.setText(aux_taquilla)
        cartelera.setText(aux_cartelera)
        val director_id = EDirector.id_a


        botonEditar.setOnClickListener{
            if(nombre.text.toString().isNotBlank() &&
                director.text.toString().isNotBlank() &&
                taquilla.text.toString().isNotBlank() &&
                cartelera.text.toString().isNotBlank() ){
                if(validar_taquilla(taquilla.text.toString()) == true &&
                    validar_cartelera(cartelera.text.toString()) == true){
                    val nuevoRestaurante = hashMapOf<String, Any>(
                        "nombre" to nombre.text.toString(),
                        "director" to director.text.toString(),
                        "taquilla" to taquilla.text.toString(),
                        "cartelera" to cartelera.text.toString(),
                        "altitud" to ubicacionSeleccionado!!.longitud.toString(),
                        "latitud" to ubicacionSeleccionado!!.latitud.toString()

                    )

                    val db = Firebase.firestore
                    val referencia = db.collection("director").document(director_id)
                        .collection("pelicula").document("${nombre.text}-${director.text}")
                    referencia.set(nuevoRestaurante).addOnSuccessListener {
                        nombre.text.clear()
                        director.text.clear()
                        taquilla.text.clear()
                        cartelera.text.clear()


                    }.addOnFailureListener {

                    }

                    abrirActividad(EPelicula::class.java)
                }else{
                    Toast.makeText(this, "Llene los campos correctamente", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Llene los campos", Toast.LENGTH_SHORT).show()
            }


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

    fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)

    }
}