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