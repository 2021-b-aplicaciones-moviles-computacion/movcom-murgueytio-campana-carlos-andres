package com.example.aplicacion01

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import java.net.URI

//import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val CODIGO_RESPUESTA_INTENT_IMPLICITO = 402

    /*var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Base de datos SQLite
        EBaseDeDatos.TablaUsuario = ESqliteHelperUsuario(this)

        if (EBaseDeDatos!=null){
            EBaseDeDatos.TablaUsuario?.crearUsuarioFormulario(
                "Carlos",
                "Carlos desc"
            )
            var consulta = EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId(
                1
            )
            Log.i("bdd", "Primera consulta = ${consulta?.nombre}")
            EBaseDeDatos.TablaUsuario?.actualizarUsuarioFormulario(
                "Andres",
                "Andres desc",
                1
            )
            Log.i("bdd", "Primera consulta = ${consulta?.nombre}")
            EBaseDeDatos.TablaUsuario?.eliminarUsuarioFormulario(
                1
            )
            Log.i("bdd", "Primera consulta = ${consulta?.nombre}")
            EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId(
                1
            )

        }

        val botonRecycledView = findViewById<Button>(R.id.btn_ir_recycler_view)
        botonRecycledView.setOnClickListener{
            //val intent = Intent(this,ACicloVida::class.java);
            //startActivity(intent)
            abrirActividadConParametros(GRecyclerView::class.java)
            //irActividad(ACicloVida::class.java) //Refactor de lo 0de arriba
        }


        val botonCicloVida = findViewById<Button>(R.id.btn_ir_ciclo_vida)
        botonCicloVida.setOnClickListener{
            //val intent = Intent(this,ACicloVida::class.java);
            //startActivity(intent)
            irActividad(ACicloVida::class.java) //Refactor de lo de arriba
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener{
            //val intent = Intent(this,ACicloVida::class.java);
            //startActivity(intent)
            irActividad(BListView::class.java) //Refactor de lo de arriba
        }

        val botonIntent = findViewById<Button>(R.id.btn_intent)
        botonIntent
            .setOnClickListener {
                abrirActividadConParametros(CIntentExplicitoParametros::class.java)
            }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentExplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            startActivityForResult(intentConRespuesta, CODIGO_RESPUESTA_INTENT_IMPLICITO)
        }

        val botonHttp = findViewById<Button>(R.id.btn_ir_http)
        botonHttp.setOnClickListener{
            abrirActividadConParametros(HttpActivity::class.java)
        }

    }




    fun abrirActividadConParametros(
        clase: Class<*>,
    ){
        val intentExplicito = Intent (this,clase)
        //Solo podemos enviar variables primitivas
        intentExplicito.putExtra("nombre", "Carlos")
        intentExplicito.putExtra("apellido", "Murgueytio")
        intentExplicito.putExtra("edad", 24)
        intentExplicito.putExtra("entrenador",BEntrenador("a","b"))

        //Esto en caso de que no funcione el resultLauncher
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)


        //startActivityForResult(intent, CODIGO_RESPUESTA_INTENT_EXPLICITO) //Esto esta desuso aunque depende de la API
        //resultLauncher.launch(intentExplicito)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Recibe todos los intent, por lo tanto debemos mandar el código del intent
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            CODIGO_RESPUESTA_INTENT_EXPLICITO -> { //cuando sea 401
                if(resultCode == RESULT_OK){
                    Log.i("intent-epn","${data?.getStringExtra("nombreModificado")}")
                }
                if (resultCode == RESULT_CANCELED){
                    Log.i("intent-epn", "Cancelado")
                }
            }
            CODIGO_RESPUESTA_INTENT_EXPLICITO -> { //cuando sea 401
                if(resultCode == RESULT_OK){
                    Log.i("intent-epn","${data?.getStringExtra("nombreModificado")}")
                }
                if (resultCode == RESULT_CANCELED){
                    if(data!=null){
                        if(data.data!=null){
                            //val uri: Uri = data.data
                            val cursor = contentResolver.query(
                                data.data!!,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            cursor?.moveToFirst()
                            val indiceTelefono = cursor?.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                            val telefono = cursor?.getString(
                                indiceTelefono!!
                            )
                            cursor?.close()
                            Log.i("intent-epn","Telefono: ${telefono}")
                        }
                    }

                }
            }
        }


    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this,clase)
        startActivity(intent)

    }
}
