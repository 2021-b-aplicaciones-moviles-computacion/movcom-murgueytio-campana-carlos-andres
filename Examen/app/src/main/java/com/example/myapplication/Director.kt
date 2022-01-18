package com.example.myapplication

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

class Director : AppCompatActivity() {
    companion object{
        var id_a = 0
        var nombre = "Director"
    }

    var posicionItem = 0
    var adapter: ArrayAdapter<EDirector>? = null
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_director)
        BaseDatos.Base= Tablas(this)
        if(BaseDatos.Base != null) {
            val Director = BaseDatos.Base!!.consultarDirectoresTodo()
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Director)
            val listViewUsuario = findViewById<ListView>(R.id.ltv_director)
            listViewUsuario.adapter = adapter
            registerForContextMenu(listViewUsuario)
        }

        val  botoncrrearDirector = findViewById<Button>(R.id.btn_crear1)
        botoncrrearDirector.setOnClickListener{
            abrirActividad(CrearDirector::class.java)
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
        id_a = adapter!!.getItem(posicionItem)!!.id_director
        nombre = adapter!!.getItem(posicionItem)!!.nombre.toString()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var Director1 = adapter!!.getItem(posicionItem)

        return when(item?.itemId){

            //Editar
            R.id.editar_director-> {

                if (Director1 != null) {
                    abrirActividadConParametros(EditarDirector::class.java,Director1)
                }

                return true
            }

            //Eliminar
            R.id.eliminar_director -> {
                //Log.i("list-view","Eliminar ${UsuarioSelect}")

                if(BaseDatos.Base!=null){

                    AlertDialog.Builder(this).apply {
                        setTitle("Alerta")
                        setMessage("¿Desea eliminar?")
                        setPositiveButton("Si"){ _: DialogInterface, _: Int ->
                            BaseDatos.Base!!.eliminarDirector(Director1!!.id_director)
                            adapter?.remove(adapter!!.getItem(posicionItem));

                        }
                        setNegativeButton("No", null)
                    }.show()


                }
                return true }
            //Ver Casas
            R.id.ver_peliculas -> {

                if (Director1 != null) {
                    abrirActividadConParametros(Pelicula::class.java,Director1)
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



    fun abrirActividadConParametros(clase: Class<*>, director: EDirector, ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("Director",director)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }
}