package com.example.aplicacion01

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val arreglo: ArrayList<Int> = arrayListOf(1,2,3,4,5)
        val adaptador = ArrayAdapter( //El adaptador recibe el contexto (clase), como se va a visualizar y el arreglo
            this,
            android.R.layout.simple_list_item_1,
            //arreglo
            BBaseDatosMemoria.arregloBEntrenador
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)

        botonAnadirListView.setOnClickListener{
            //anadirItem(adaptador,arreglo,1)
            anadirItem(adaptador,BBaseDatosMemoria.arregloBEntrenador,1)
        }

//        listView.setOnClickListener{
//            Log.i("list-view","Click ")
//        }

//        listView.setOnItemLongClickListener{ parent, view, position, id->
//            Log.i("list-view","LONG CLICK ${arreglo[position]}")
//            return@setOnItemLongClickListener true
//        }

        //1) registrar menu contextual
        registerForContextMenu(listView) //Podemos poner this.registerForContextMenu(listView) que es lo mismo pero por kotlin no es necesario por herencia
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater //Esto lo que hace es intentar traer el xml y llenarle en otro lado
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = info.position
        Log.i("context-menu", "ID: ${id}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar -> {
                Log.i("context-menu", "Editar posicion: ${idItemSeleccionado}")
                abrirDialogo()
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("context-menu", "Eliminar posicion: ${idItemSeleccionado}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Titulo builder")
        //builder.setMessage("Descripcion")

        val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)

        val seleccionPrevia = booleanArrayOf(//Estas son las opciones preseleccionadas
            true,
            false,
            false
        )

        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {dialog,
             which,
             isChecked-> Log.i("dialogo","Dio click en el item ${which}")
            }
        )

        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener{dialog, which-> Log.i("dialogo","Hola =)")})

        builder.setNegativeButton("Cancelar",null)


        val dialogo = builder.create()
        dialogo.show()
    }


    fun anadirItem(
        /*adaptador: ArrayAdapter<Int>,
        arreglo: ArrayList<Int>,
        valor: Int*/
        adaptador: ArrayAdapter<BEntrenador>,
        arreglo: ArrayList<BEntrenador>,
        valor: Int
    ){
        //arreglo.add(valor)
        arreglo.add(BEntrenador("otroEntrenador","asd"))
        adaptador.notifyDataSetChanged()

    }



}