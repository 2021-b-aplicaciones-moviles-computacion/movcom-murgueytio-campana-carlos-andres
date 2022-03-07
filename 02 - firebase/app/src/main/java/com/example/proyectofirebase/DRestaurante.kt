package com.example.proyectofirebase

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DRestaurante : AppCompatActivity() {
    var query: Query? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drestaurante)
        val botonCrear = findViewById<Button>(R.id.btn_crear_restaurante)
        botonCrear
            .setOnClickListener{
                crearRestaurante()
            }

        val botonDatosPrueba = findViewById<Button>(R.id.btn_datos_prueba)
        botonCrear
            .setOnClickListener{
                crearDatosPrueba()
            }

        val botonConsultar = findViewById<Button>(R.id.btn_consultar)
        botonCrear
            .setOnClickListener{
                consultar()
            }
    }

    fun consultar(){

        /*
        val db = Firebase.firestore
        //Cuando trabajamos con varios documentos
        val citiesRef = db
            .collection("cities")
            .orderBy("population")
            .limit(2) //Solo tomamos  dos registros
        /*
        citiesRef
            //.document("BJ")
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    Log.i("consultas","${ciudad.data} ${ciudad.id}")
                }

            }
            .addOnFailureListener{}

        //Cuando trabajamos con un solo documento
        val citiesRefUnico = db
            .collection("cities")
            //.orderBy("population")
            //.limit(2) //Solo tomamos  dos registros
        citiesRefUnico
            .document("BJ")
            .get()
            .addOnSuccessListener {
                Log.i("consultas","${it.data}")
            }
            .addOnFailureListener{}
           */

        //Buscar por un solo campo
        citiesRef
            .whereEqualTo("country","China")
            .get()
            .addOnSuccessListener {
                Log.i("consultas", "${it.documents}")
                for (ciudad in it){
                    Log.i("consultas","${ciudad.data}")
                    Log.i("consultas","${ciudad.id}")
                }
            }
            .addOnFailureListener{}

        //Vamos a buscar por dos o mas elementos con ==
        citiesRef
            .whereEqualTo("regions",false)
            .whereArrayContains("regions", arrayListOf("socal", "norcal"))
            .get()
            .addOnSuccessListener {
                Log.i("consultas", "${it.documents}")
                for (ciudad in it){
                    Log.i("consultas","${ciudad.data}")
                }
            }
            .addOnFailureListener{}

        //Buscamos por dos o mas elementos con >= o ==
        citiesRef
            .whereEqualTo("regions",true)
            .whereArrayContains("population", 1000000)
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    Log.i("consultas","== array-contains ${ciudad.data}")
                }
            }
            .addOnFailureListener{}

        //Buscamos por dos o mas elementos con <=
        citiesRef
            .whereEqualTo("regions",true)
            .whereArrayContains("population", 4000000)
            .orderBy("population",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    Log.i("consultas","== array-contains ${ciudad.data}")
                }
            }
            .addOnFailureListener{}
        */

        //PAGINACION

        val db = Firebase.firestore
        val refCities = db
            .collection("cities")
            .orderBy("population")
            .limit(2)

        var tarea: Task<QuerySnapshot>?  = null
        if(query==null){
            tarea = refCities.get()
        } else {
            tarea = query!!.get()
        }
        if (tarea!=null){
            tarea
                .addOnSuccessListener { documentSnapshots->
                    guardarQuery(documentSnapshots, refCities)
                    for (ciudad in documentSnapshots){
                        Log.i("consultas", "${ciudad.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("consultas", "ERROR: ${it}")
                }

        }
    }

    fun guardarQuery(documentSnapshot: QuerySnapshot, refCities: Query){
        if(documentSnapshot.size()>0){
            val ultimoDocumento = documentSnapshot.documents[documentSnapshot.size()-1]
            query = refCities
                .startAfter(ultimoDocumento)
        } else {

        }

    }

    fun transaccion(){

    }

    fun crearRestaurante(){
        val editTextNombre = findViewById<EditText>(R.id.et_nombre_restaurante)
        val nuevoRestaurante = hashMapOf<String, Any>(
            "nombre" to editTextNombre.text.toString()
        )

        val db = Firebase.firestore
        val referencia = db.collection("restaurante")
        referencia
            .add(nuevoRestaurante)
            .addOnSuccessListener {
                editTextNombre.text.clear()
            }
            .addOnFailureListener{

            }

    }

    fun crearDatosPrueba(){
        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast")
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu")
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei")
        )
        cities.document("BJ").set(data5)
    }





}