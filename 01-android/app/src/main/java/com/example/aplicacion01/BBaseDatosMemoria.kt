package com.example.aplicacion01

class BBaseDatosMemoria() {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
    }
    init{
        arregloBEntrenador
            .add(
                BEntrenador("Carlos","a@a.com")
            )
        arregloBEntrenador
            .add(
                BEntrenador("Jose","a@a.com")
            )
        arregloBEntrenador
            .add(
                BEntrenador("Daniel","a@a.com")
            )
    }
}