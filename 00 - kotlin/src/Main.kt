import java.util.*

fun main() {
    println("Hola mundo"); //El ; no es requerido
    //En java:
    //Int edad = 12;

    //En kotlyn se maneja el duck typing
    //tipos de variables
    //Inmutables (val) Ponemos el tipo de dato con 2 puntos, son inmutables porque no se puede cambiar su valor

    val inmutable:String = ""

    //Mutables (var)

    var mutable:String = ""
    mutable = "Carlos";


    //Sintaxis y duck tipyng
    val ejemploVariable = "nombre"
    var edadEjemplo = 12
    //edadEjemplo = 12.2 No se puede pq ya esta como int

    //Tipos de variable JAVA
    val nombre:String ="Carlos"
    val sueldo:Double = 14.5
    val estadoCivil:Char = 'S'
    val fechaNacimiento: Date = Date()


    if (true){

    }else{

    }

    //Los switchs aqui se los manejan como when
    val estadoCivilWhen:String = "S"

    when (estadoCivilWhen){
        ("S") -> {
            println("Acercarse")
        }
        "C" -> {
            println("Alejarse")
        }
        "UN" -> println("Hablar")
        else -> println("No reconocido")
    }


    val coqueteo = if (estadoCivilWhen == "S") "SI" else "NO"

    imprimirNombre("Adrian")

    calcularsueldo(100.00, 14.00, 25.00)


    //Parametros Nombrados
    calcularsueldo( //Forma para mover el orden de los parametros en las funciones
        bonoEspecial = 15.00,
        //14.00,
        sueldo = 25.00
    )


    //Arreglos y operadores

    val arregloEstatico: Array<Int> = arrayOf(1,2,3)

    val arregloDinamico: ArrayList<Int> = arrayListOf(1,2,3)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //Operadores sirven para ambos arreglos
    val respuestaForEach: Unit = arregloDinamico
        .forEach{valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }

    arregloDinamico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor: ${valorActual}, Indice: ${indice}")
        }
    println(respuestaForEach)

    //Map modifica el arreglo y envia uno nuevo
    val respuestaMapDos =  arregloDinamico.map{it+15}
    println(respuestaMapDos)


    //Filter para filtar el arreglo
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter{it<=5}
    println(respuestaFilter)
    println(respuestaFilterDos)

    val respuestaAny: Boolean = arregloDinamico
        .any{ valorActual: Int ->
            return@any (valorActual>5)
        }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico
        .all{ valorActual: Int ->
            return@all (valorActual>5)
        }
    println(respuestaAll)

    val respuestaReduce: Int = arregloDinamico
        .reduce{
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)
        }
    println(respuestaReduce)

    val arregloDanio = arrayListOf<Int>(12,15,8,10)
    val respuestaReduceFold = arregloDanio
        .fold(100,
            { acumulado, valorActualInteracion ->
                return@fold acumulado - valorActualInteracion
        })
    println(respuestaReduceFold)

}

fun imprimirNombre(nombre:String) :Unit{ //Unit es el void y es opcional
    println("Nombre: ${nombre}")
}

fun calcularsueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional, por defecto
    bonoEspecial:Double? = null //Opcional, null y el ? significa nullable
): Double {
    //String -> String?
    //Int ->Int?
    //Date -> Date? Son nullables
    if(bonoEspecial ==null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos:Int
    ){
        numeroUno = uno;
        numeroDos = dos;
        println("Inicializar")

    }
}

abstract class Numeros( //Constructor primario despues de los parentesis
    val numeroUno: Int,
    val numeroDos: Int
){
    init{
        println("Inicializar")
    }
}

class Suma(
    uno: Int,
    dos: Int,
): Numeros( //El : llama al constructor padre
    uno,
    dos
) {
    init { // Bloque de codigo del constructor primario
        this.numeroUno
        this.numeroDos
    }
    fun sumar(): Int {
        val total: Int = numeroUno + numeroDos
        return total
    }
}
