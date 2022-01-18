package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Tablas(context: Context):SQLiteOpenHelper(context,"examen",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaDirector=
            """
            CREATE TABLE DIRECTOR(
            ID_DIRECTOR INTEGER PRIMARY KEY AUTOINCREMENT,
            NOMBREDIRECTOR VARCHAR(50),
            FECHANACIMIENTO VARCHAR(50),
            NACIONALIDAD VARCHAR(50),
            ISALIVE VARCHAR(50));  
            """.trimIndent()

        Log.i("bdd", "Crearcion tabla Cine")
        db?.execSQL(scriptCrearTablaDirector)


        val scriptCrearTablaPelicula=
            """
            CREATE TABLE PELICULA(
            ID_PELICULA INTEGER PRIMARY KEY AUTOINCREMENT,
            ID_DIRECTOR INTEGER,
            NOMBREPELICULA VARCHAR(50),
            VALORACIONPELICULA DOUBLE,
            PRESUPUESTO DOUBLE,
            ENCARTELERA VARCHAR(50),
            foreign key(ID_DIRECTOR) references DIRECTOR(ID_DIRECTOR)
            );  
            """.trimIndent()

        Log.i("bdd", "Creacion tabla Pelicula")
        db?.execSQL(scriptCrearTablaPelicula)
    }

    fun crearDirector(nombreDirector: String, fechaNacimiento: String, nacionalidad:String , isAlive:String):Boolean{
        val conexionEscritura= writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("NOMBREDIRECTOR", nombreDirector)
        valoresAGuardar.put("FECHANACIMIENTO", fechaNacimiento)
        valoresAGuardar.put("NACIONALIDAD", nacionalidad)
        valoresAGuardar.put("ISALIVE", isAlive)

        val resultadoEscritura: Long= conexionEscritura.insert("DIRECTOR", null,valoresAGuardar)
        conexionEscritura.close()
        if(resultadoEscritura.toInt()!=-1){
            return true
        }else{
            return false
        }
    }

    fun consultarDirectoresTodo(): ArrayList<EDirector> {
        val scriptConsultarUsuario = "SELECT * FROM DIRECTOR"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        var arregloUsuario = arrayListOf<EDirector>()

        if(existeUsuario){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                if(id!=null){
                    arregloUsuario.add(
                        EDirector(id,
                            resultaConsultaLectura.getString(1),
                            resultaConsultaLectura.getString(2),
                            resultaConsultaLectura.getInt(3),
                            SimpleDateFormat("dd/MM/yyyy").parse(resultaConsultaLectura.getString(5))
                        )
                    )
                }
            }while(resultaConsultaLectura.moveToNext())
        }

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloUsuario
    }

    fun editarDirector(nombreDirector: String, fechaNacimiento: Date?, nacionalidad:String, isAlive:String, idActualizar:Int): Boolean {
        val conexionEscritura = writableDatabase
        val valorAActualizar = ContentValues()

        valorAActualizar.put("NOMBREDIRECTOR", nombreDirector)
        valorAActualizar.put("NACIONALIDAD", nacionalidad)
        valorAActualizar.put("ISALIVE", isAlive)


        val resultadoActualizacion = conexionEscritura.update("DIRECTOR", valorAActualizar, "ID_DIRECTOR=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()

        if(resultadoActualizacion.toInt()!=-1){
            return true
        }else{

            return false
        }

    }

    fun eliminarDirector(id: Int):Boolean{
        val conexionEscritura = readableDatabase
        val resultadoEliminacion = conexionEscritura.delete("DIRECTOR","ID_DIRECTOR=?",
            arrayOf(id.toString()))
        conexionEscritura.close()

        if(resultadoEliminacion.toInt()!=-1){
            return true
        }else{
            return false
        }

    }

    fun consultarPelicula(id_Director: Int): ArrayList<EPelicula> {
        val scriptConsultaCine = "SELECT * FROM PELICULA WHERE ID_DIRECTOR = ${id_Director}"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaCine, null)
        val existePelicula = resultaConsultaLectura.moveToFirst()
        val arregloPelicula = arrayListOf<EPelicula>()
        if(existePelicula){
            do{
                val id = resultaConsultaLectura.getInt(0)
                val nombrePelicula = resultaConsultaLectura.getString(2)
                val valoracionPelicula = resultaConsultaLectura.getDouble(3)
                val presupuesto = resultaConsultaLectura.getDouble(4)
                var cartelera = resultaConsultaLectura.getString(5).toBoolean()
                if(id!=null){
                    arregloPelicula.add(
                        EPelicula(id, id_Director, nombrePelicula, valoracionPelicula, presupuesto, cartelera))
                }
            }while(resultaConsultaLectura.moveToNext())
        }

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloPelicula
    }

    fun editarPelicula(nombrePelicula:String, valoracionPelicula:Double, presupuesto:Double, cartelera:Boolean, idActualizar:Int): Boolean {

        val conexionEscritura = writableDatabase
        val valorAActualizar = ContentValues()

        valorAActualizar.put("NOMBREPELICULA", nombrePelicula)
        valorAActualizar.put("VALORACIONPELICULA", valoracionPelicula)
        valorAActualizar.put("PRESUPUESTO", presupuesto)
        valorAActualizar.put("ENCARTELERA", cartelera)

        val resultadoActualizacion = conexionEscritura.update("PELICULA", valorAActualizar, "ID_PELICULA=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()

        if(resultadoActualizacion.toInt()!=-1){

            return true
        }else{

            return false
        }

    }

    fun bolean_entero(a:Int):Boolean{
        if(a == 0){
            return false
        }
        if(a ==1){
            return true
        }

        else return false

    }

    fun crearPelicula(nombrePelicula:String, valoracionPelicula:Double, presupuesto:Double, cartelera:Boolean, idDirector:Int):Boolean{

        val conexionEscritura= writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("NOMBREPELICULA", nombrePelicula)
        valoresAGuardar.put("VALORACIONPELICULA", valoracionPelicula)
        valoresAGuardar.put("PRESUPUESTO", presupuesto)
        valoresAGuardar.put("ENCARTELERA", cartelera)
        valoresAGuardar.put("ID_DIRECTOR",idDirector)

        val resultadoEscritura: Long= conexionEscritura.insert("PELICULA", null,valoresAGuardar)
        conexionEscritura.close()
        if(resultadoEscritura.toInt()!=-1){
            return true
        }else{
            return false
        }
    }


    fun eliminarPelicula(id: Int):Boolean{
        val conexionEscritura = readableDatabase
        val resultadoEliminacion = conexionEscritura.delete("PELICULA","ID_PELICULA=?",
            arrayOf(id.toString()))
        conexionEscritura.close()

        if(resultadoEliminacion.toInt()!=-1){

            return true
        }else{
            return false
        }

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}