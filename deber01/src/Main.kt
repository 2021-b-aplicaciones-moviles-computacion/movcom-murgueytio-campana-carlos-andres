import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val txtDirector = "src/director.txt"
    val txtPelicula = "src/pelicula.txt"
    val scanner = Scanner(System.`in`)
    val listaDirectores = leerDirectores(txtDirector)
    val listaPeliculas = leerPeliculas(txtPelicula)
    //Create, Read, Update, delete
    //imprimirDirectores(listaDirectores)
    //crearDirector(txtDirector, 0,true)
    //actualizarDirector(listaDirectores,txtDirector)
    //borrarDirector(listaDirectores,txtDirector)


    imprimirPeliculas(listaPeliculas)
    //crearPelicula(txtPelicula,0,true)
    borrarPelicula(listaPeliculas,txtPelicula)

}
fun leerArchivo(nombreArchivo: String):ArrayList<ArrayList<String>>{
    val unItem = ArrayList<String>()
    val variosItem = arrayListOf(ArrayList<String>())
    try{
        val miArchivo = File(nombreArchivo)
        val lector = Scanner(miArchivo)
        while(lector.hasNextLine()){
            val datos : String = lector.nextLine()
            val tokenizer = StringTokenizer(datos,",")
            while(tokenizer.hasMoreTokens()){
                unItem.add(tokenizer.nextToken())
            }
            variosItem.add(unItem.clone() as ArrayList<String>)
            unItem.clear()
        }
        lector.close()
    } catch (e: FileNotFoundException){
        println("No se encontró el archivo")
        e.printStackTrace()
    }
    variosItem.removeAt(0)
    return variosItem
}

fun imprimirDirectores(directores: ArrayList<Director>){
    System.out.format("%-12s%-25s%-35s%-25s%-20s\n", "idDirector","Nombre del Director","Fecha Nacimiento","Nacionalidad","Esta vivo")
    directores.forEach {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        var fecha: String? = formatoFecha.format(it.getFechaNacimiento())
        System.out.format("%-12s%-25s%-35s%-25s%-20s\n", it.getidDirector(), it.getNombreDirector(), it.getFechaNacimiento(), it.getNacionalidad(), it.getIsAlive())
    }
}

fun crearDirector(rutaArchivo: String, idDirector : Int, nuevoDirector: Boolean){
    val sc = Scanner(System.`in`)
    var id :Int =  idDirector
    if (nuevoDirector){
        println("Ingrese el id del Director:")
        id = sc.nextLine().toInt()
    }
    println("Ingrese el nombre del Director:")
    val nombre = sc.nextLine()
    println("Ingrese la fecha de nacimiento del Director:")
    val fecha = sc.nextLine()
    println("Ingrese la nacionalidad del Director:")
    val nacionalidad = sc.nextLine()
    println("Este director aun se encuentra vivo? S/N")
    var opcCrear = sc.nextLine()
    val isAlive : Boolean
    if (opcCrear ==  "S"){
        isAlive = true
    }else {
        isAlive = false
    }
    try{
        FileWriter(rutaArchivo, true).use {
                fileWriter -> BufferedWriter(fileWriter).use{
                    bufferedWriter -> PrintWriter(bufferedWriter).use {
                        out -> out.print("\n"+"${id},${nombre},${fecha},${nacionalidad},${isAlive},")
                    }
                }
        }
    } catch (e: IOException){
        //
    }
    borrarLineaTxt(rutaArchivo,"")
    println("Director registrado con éxito")
}

fun leerDirectores(archivo:String): ArrayList<Director>{
    val directores = ArrayList<Director>()
    val archivoDirectores = leerArchivo(archivo)
    var str: String?
    archivoDirectores.forEach{
        val fechaNac = SimpleDateFormat("dd/MM/yyyy").parse(it[2])
        directores.add(Director(it[0].toInt(), it[1].toString(), fechaNac, it[3].toString(), it[4].toBoolean()))
    }
    return directores
}

fun buscarDirector(indexColumna: Int, texto:String, archivo: ArrayList<Director>) : ArrayList<Director>{
    if(indexColumna == 0){
        return (archivo.filter {
            return@filter (it.getidDirector() == texto.toInt())
        })as ArrayList<Director>
    }
    if(indexColumna == 1){
        return (archivo.filter {
            return@filter (it.getNombreDirector() == texto.toString())
        })as ArrayList<Director>
    }
    if(indexColumna == 2){
        //val fecha = SimpleDateFormat("dd/MM/yyyy").parse(texto)
        return (archivo.filter {
            return@filter (it.getFechaNacimiento() == SimpleDateFormat("dd/MM/yyyy").parse(texto))
        })as ArrayList<Director>
    }
    if(indexColumna == 3){
        return (archivo.filter {
            return@filter (it.getNacionalidad() == texto.toString())
        })as ArrayList<Director>
    }
    return (archivo.filter {
        return@filter (it.getIsAlive() == texto.toBoolean())
    })as ArrayList<Director>
}

fun actualizarDirector(listaDirectores: ArrayList<Director>, rutaArchivo: String){
    val sc = Scanner(System.`in`)
    println("Esta es la lista actual de directores:")
    imprimirDirectores(listaDirectores)
    println("Ingrese el id del director a actualizar ->")
    val idAEditar = sc.nextLine()
    println("Ingrese el nombre del Director:")
    val nombre = sc.nextLine()
    println("Ingrese la fecha de nacimiento del Director:")
    val fecha = sc.nextLine()
    println("Ingrese la nacionalidad del Director:")
    val nacionalidad = sc.nextLine()
    println("Este director aun se encuentra vivo? S/N")
    var opcCrear = sc.nextLine()
    val isAlive : Boolean
    if (opcCrear ==  "S"){
        isAlive = true
    }else {
        isAlive = false
    }
    try{
        FileWriter(rutaArchivo, true).use {
                fileWriter -> BufferedWriter(fileWriter).use{
                    bufferedWriter -> PrintWriter(bufferedWriter).use {
                        out -> out.print("\n"+"${idAEditar},${nombre},${fecha},${nacionalidad},${isAlive},")
                    }
                }
        }
    } catch (e: IOException){
        //
    }
    println("Director actualizado con éxito")
}

fun borrarDirector(listaDirectores: ArrayList<Director>, rutaArchivo: String){
    val sc = Scanner(System.`in`)
    var idD : String?
    var nombre: String?
    var nacionalidad:String?
    var isAlive:Boolean?
    println("Ingrese el id del Director que desea eliminar ->")
    val id = sc.nextLine()
    val delete = buscarDirector(0,id,listaDirectores)
    delete.forEach{
        idD = it.getidDirector().toString()
        nombre = it.getNombreDirector().toString()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        var fecha = formatoFecha.format(it.getFechaNacimiento())
        nacionalidad = it.getNacionalidad().toString()
        isAlive = it.getIsAlive()
        borrarLineaTxt(rutaArchivo,"${idD},${nombre},${fecha},${nacionalidad},${isAlive},")
        borrarLineaTxt(rutaArchivo,"")
        println("Registro del director eliminado con éxito")
    }
}

fun imprimirPeliculas(peliculas: ArrayList<Pelicula>){
    System.out.format("%-12s%-12s%-25s%-25s%-25s%-25s\n", "idPelicula","idDirector","Nombre Pelicula","Valoracion","Presupuesto","En cartelera")
    peliculas.forEach{
        System.out.format("%-12s%-12s%-25s%-25s%-25s%-25s\n",it.getidPelicula(),it.getidDirector(),it.getNombrePelicula(),it.getValoracionPelicula(),it.getPresupuesto(),it.getEnCartelera())
    }
}

fun leerPeliculas(archivo:String):ArrayList<Pelicula>{
    val pelis = ArrayList<Pelicula>()
    val archivoPelis = leerArchivo(archivo)
    var str: String?
    archivoPelis.forEach{
        pelis.add(Pelicula(it[0].toInt(),it[1].toInt(),it[2].toString(),it[3].toDouble(),it[4].toDouble(),it[5].toBoolean()))
    }
    return pelis
}

fun crearPelicula(rutaArchivo: String, idPelicula: Int, nuevaPelicula: Boolean){
    val sc = Scanner(System.`in`)
    var id :Int =  idPelicula
    if (nuevaPelicula){
        println("Ingrese el id de la Pelicula:")
        id = sc.nextLine().toInt()
    }
    println("Ingrese el id del director:")
    val idDirector = sc.nextLine()
    println("Ingrese el nombre de la pelicula:")
    val nombrePel = sc.nextLine()
    println("Ingrese la valoracion de la pelicula:")
    val valoracion = sc.nextLine()
    println("Ingrese el presupuesto de la pelicula:")
    val presupuesto = sc.nextLine()
    println("Esta pelicula se encuentra en cartelera? S/N")
    var opcCrear = sc.nextLine()
    val enCartelera : Boolean
    if (opcCrear ==  "S"){
        enCartelera = true
    }else {
        enCartelera = false
    }
    try{
        FileWriter(rutaArchivo, true).use {
                fileWriter -> BufferedWriter(fileWriter).use{
                bufferedWriter -> PrintWriter(bufferedWriter).use {
                out -> out.print("\n"+"${id},${idDirector},${nombrePel},${valoracion},${presupuesto},${enCartelera},")
        }
        }
        }
    } catch (e: IOException){
        //
    }
    borrarLineaTxt(rutaArchivo,"")
    println("Pelicula registrada con éxito")
}

fun actualizarPelicula(peliculas: ArrayList<Pelicula>, rutaArchivo: String){
    val sc = Scanner(System.`in`)
    println("Esta es la lista actual de peliculas:")
    imprimirPeliculas(peliculas)
    println("Ingrese el id de la pelicula a actualizar ->")
    val idAEditar = sc.nextLine()
    println("Ingrese el id del Director:")
    val idDir = sc.nextLine()
    println("Ingrese el nombre de la pelicula:")
    val nombre = sc.nextLine()
    println("Ingrese la valoracion de la pelicula:")
    val valoracion = sc.nextLine()
    println("Ingrese el presupuesto de la pelicula:")
    val presupuesto = sc.nextLine()
    println("Esta pelicula aun se encuentra en cartelera? S/N")
    var opcCrear = sc.nextLine()
    val enCartelera : Boolean
    if (opcCrear ==  "S"){
        enCartelera = true
    }else {
        enCartelera = false
    }
    try{
        FileWriter(rutaArchivo, true).use {
                fileWriter -> BufferedWriter(fileWriter).use{
                    bufferedWriter -> PrintWriter(bufferedWriter).use {
                        out -> out.print("\n"+"${idAEditar},${idDir},${nombre},${valoracion},${presupuesto},${enCartelera},")
                    }
                }
        }
    } catch (e: IOException){
        //
    }
    println("Pelicula actualizada con éxito")
}

fun buscar2Pelicula(indexColumna: Int, texto:String, archivo: ArrayList<Pelicula>) : ArrayList<Pelicula>{
    if(indexColumna == 0){
        return (archivo.filter {
            return@filter (it.getidPelicula() == texto.toInt())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 1){
        return (archivo.filter {
            return@filter (it.getidDirector() == texto.toInt())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 2){
        return (archivo.filter {
            return@filter (it.getNombrePelicula() == texto.toString())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 3){
        return (archivo.filter {
            return@filter (it.getValoracionPelicula() == texto.toDouble())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 4){
        return (archivo.filter {
            return@filter (it.getPresupuesto() == texto.toDouble())
        })as ArrayList<Pelicula>
    }
    return (archivo.filter {
        return@filter (it.getEnCartelera() == texto.toBoolean())
    })as ArrayList<Pelicula>
}

fun buscarPelicula(indexColumna: Int, texto:String, archivo: ArrayList<Pelicula>) : ArrayList<Pelicula>{
    if(indexColumna == 0){
        return (archivo.filter {
            return@filter (it.getidPelicula() == texto.toInt())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 1){
        return (archivo.filter {
            return@filter (it.getidDirector() == texto.toInt())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 2){
        return (archivo.filter {
            return@filter (it.getNombrePelicula() == texto.toString())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 3){
        return (archivo.filter {
            return@filter (it.getValoracionPelicula() == texto.toDouble())
        })as ArrayList<Pelicula>
    }
    if(indexColumna == 4){
        return (archivo.filter {
            return@filter (it.getPresupuesto() == texto.toDouble())
        })as ArrayList<Pelicula>
    }
    return (archivo.filter {
        return@filter (it.getEnCartelera() == texto.toBoolean())
    })as ArrayList<Pelicula>
}

fun borrarPelicula(listaPeliculas: ArrayList<Pelicula>, rutaArchivo: String){
    val sc = Scanner(System.`in`)
    var idPeli: String?
    var idD : String?
    var nombre: String?
    var valoracion: Double?
    var presupuesto: Double?
    var enCartelera: Boolean?
    println("Ingrese el id de la pelicula que desea eliminar ->")
    val id = sc.nextLine()
    val delete = buscarPelicula(0,id,listaPeliculas)
    delete.forEach{
        idPeli = it.getidPelicula().toString()
        idD = it.getidDirector().toString()
        nombre = it.getNombrePelicula().toString()
        valoracion = it.getValoracionPelicula()
        presupuesto = it.getPresupuesto()
        enCartelera = it.getEnCartelera()
        borrarLineaTxt(rutaArchivo,"${idPeli},${idD},${nombre},${valoracion},${presupuesto},${enCartelera},")
        borrarLineaTxt(rutaArchivo,"")
        println("Registro de la pelicula eliminado con éxito")
    }
}

fun borrarLineaTxt(archivoOrg: String, lineaABorrar: String){
    try{
        val archivo = File(archivoOrg)
        if(!archivo.isFile){
            println("No existe ese archivo")
            return
        }
        val archivoTemp = File(archivo.absolutePath + ".tmp")
        val bufferedReader = BufferedReader(FileReader(archivo))
        val printWriter = PrintWriter(FileWriter(archivoTemp))
        var linea : String? = null

        while (bufferedReader.readLine().also { linea = it } != null) {
            if (linea!!.trim { it <= ' ' } != lineaABorrar) {
                printWriter.println(linea)
                printWriter.flush()
            }
        }
        printWriter.close()
        bufferedReader.close()

        if(!archivo.delete()){
            println("No se pudo borrar el archivo")
            return
        }

        if(!archivoTemp.renameTo(archivo)) println("No se puedo renombrar el archivo")
    } catch (ex: FileNotFoundException) {
        ex.printStackTrace()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
}






















