package com.example.eamenprueba

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class EDirector : AppCompatActivity() {
    companion object{
        var id_a = ""
        var nombre = "director"
    }
    var arregloDirectores = ArrayList<Director>()
    var adapter: ArrayAdapter<Director>? = null
    var posicionItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edirector)

        val  botonDirector = findViewById<Button>(R.id.btn_crear_director)
        botonDirector.setOnClickListener{
            abrirActividad(CrearDirector::class.java)
        }

        val db = Firebase.firestore
        var documentoRestaurante: (MutableList<DocumentSnapshot>)
        var referencia = db.collection("director")
            .get()
            .addOnSuccessListener {
                documentoRestaurante = it.documents
                documentoRestaurante.forEach { iteracion ->
                    arregloDirectores.add(Director(iteracion.get("nombre").toString(),
                        iteracion.get("nacionalidad").toString(),
                        iteracion.get("isAlive").toString().toInt(),
                        SimpleDateFormat("dd/MM/yyyy").parse(iteracion.get("fecha").toString()),))

                }
                if(arregloDirectores.size > 0) {
                    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloDirectores)
                    val listViewUsuario = findViewById<ListView>(R.id.ltv_director)
                    listViewUsuario.adapter = adapter
                    registerForContextMenu(listViewUsuario)
                }else{

                }
            }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menudirector,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItem = id
        id_a = "${adapter!!.getItem(posicionItem)!!.nombre.toString()}-${adapter!!.getItem(posicionItem)!!.nacionalidad.toString()}"
        nombre = adapter!!.getItem(posicionItem)!!.nombre.toString()





    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var director1 = adapter!!.getItem(posicionItem)

        return when(item?.itemId){

            //Editar
            R.id.editar_director-> {

                if (director1 != null) {
                    this.startActivity(Intent(this,EditarDirector::class.java).putExtra("director",director1))
                }

                return true
            }

            //Eliminar
            R.id.eliminar_director -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Alerta")
                    setMessage("¿Desea Eliminar?")
                    setPositiveButton("Si"){_: DialogInterface, _: Int ->
                        val db = Firebase.firestore
                        var refCasa = db.collection("pelicula")
                        EPelicula.arregloPeliculasAUX2.forEach {
                            db.collection("director").document("${director1!!.nombre}-${director1!!.nacionalidad}")
                                .collection("pelicula").document("${it.nombre}-${it.director}") .delete()
                        }

                        db.collection("director").document("${director1!!.nombre}-${director1!!.nacionalidad}").delete()
                        adapter?.remove(adapter!!.getItem(posicionItem));

                    }
                    setNegativeButton("No", null)
                }.show()


                return true }
            //Ver Casas
            R.id.ver_peliculas -> {


                if (director1 != null) {
                    this.startActivity(Intent(this,EPelicula::class.java).putExtra("director",director1))
                }



                return true }

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