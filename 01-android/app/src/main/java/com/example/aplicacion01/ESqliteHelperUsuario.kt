package com.example.aplicacion01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario(
    context: Context?
): SQLiteOpenHelper(
    context,
    "moviles",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
                CREATE TABLE USUARIO (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        Log.i("bbd", "Creando tabla de usuarios")
        db?.execSQL(scriptCrearTablaUsuario)
    }

    // Establecemos el contrato para la operacion en la BD
    fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String
    ): Boolean{
        // Base de datos de escritura
        val baseDatosEscritura = writableDatabase

        // Mapeamos los valores que vamos a guardar en la base de datos
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        // Hacemos la llamada a base de datos
        val resultadoEscritura: Long = baseDatosEscritura.insert(
            "USUARIO",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()

        // Nos aseguramos de que la operacion se realizo en base de datos
        return resultadoEscritura.toInt() != -1
    }

    fun consultarUsuarioPorId(
        id: Int
    ): EUsuarioBDD {
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID= ${id}"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0,"","")
        if (existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id!=null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            }while(resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun eliminarUsuarioFormulario(
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "USUARIO",
                "id=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1)false else true
    }

    fun actualizarUsuarioFormulario(
        nombre:String,
        descripcion: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion",descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "USUARIO",
                valoresAActualizar,
                "id=?",
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1)false else true
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}