package com.example.eamenprueba

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EPelicula : AppCompatActivity() {

companion object{
    var arregloPeliculasAUX2 = ArrayList<Pelicula>()
}
    var arregloPeliculasAUX = ArrayList<Pelicula>()
    var adapter: ArrayAdapter<Pelicula>? = null
    var posicionItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epelicula)

        var director = findViewById<TextView>(R.id.tvpelicula)
        var nombre = EDirector.nombre
        director.setText(nombre)
        val id = EDirector.id_a
        val db = Firebase.firestore

        var documentoRestaurante: (MutableList<DocumentSnapshot>)
        var referencia = db.collection("director")
            .document(id).collection("pelicula")
            .get()
            .addOnSuccessListener {
                documentoRestaurante = it.documents
                documentoRestaurante.forEach { iteracion ->
                    arregloPeliculasAUX.add(Pelicula(iteracion.get("nombre").toString(),
                        iteracion.get("director").toString(),
                        iteracion.get("taquilla").toString().toDouble(),
                        iteracion.get("cartelera").toString().toBoolean(),
                        iteracion.get("altitud").toString().toDouble(),
                        iteracion.get("latitud").toString().toDouble(),))

                }
                arregloPeliculasAUX2 = arregloPeliculasAUX
                if(arregloPeliculasAUX.size > 0) {
                    adapter =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloPeliculasAUX)
                    val listViewUsuario = findViewById<ListView>(R.id.ltv_pelicula)
                    listViewUsuario.adapter = adapter
                    registerForContextMenu(listViewUsuario)
                }else{

                }
            }

        val botoncrearPelicula = findViewById<Button>(R.id.btn_crear_peliula)
        botoncrearPelicula.setOnClickListener{
            abrirActividad(CrearPelicula::class.java)

        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menupelicula,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItem = id
        Log.i("bdd","Selección: ${posicionItem}")


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var pelicula = adapter!!.getItem(posicionItem)

        return when(item?.itemId){


            R.id.editar_pelicula-> {

                if (pelicula != null) {
                    this.startActivity(Intent(this,EditarPelicula::class.java).putExtra("pelicula",pelicula))
                }



                return true
            }


            R.id.eliminar_pelicula -> {

                AlertDialog.Builder(this).apply {
                    setTitle("Alerta")
                    setMessage("¿Desea Eliminar?")
                    setPositiveButton("Si"){ _: DialogInterface,_: Int ->
                        val db = Firebase.firestore
                        val director_id = EDirector.id_a
                        db.collection("director").document(director_id).collection("pelicula")
                            .document("${pelicula!!.nombre}-${pelicula!!.director}").delete()
                        adapter?.remove(adapter!!.getItem(posicionItem));

                    }
                    setNegativeButton("No",null)
                }.show()

                return true }

            R.id.mapa_pelicula ->{

              if (pelicula != null) {
                    this.startActivity(Intent(this,FMapsActivity::class.java).putExtra("pelicula",pelicula))
                }

                return true
            }


            else -> super.onContextItemSelected(item)
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