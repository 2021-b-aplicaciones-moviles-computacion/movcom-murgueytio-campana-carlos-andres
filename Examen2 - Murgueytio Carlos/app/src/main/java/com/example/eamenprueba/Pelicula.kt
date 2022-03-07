package com.example.eamenprueba

import java.io.Serializable

class Pelicula(  var nombre:String?, var director:String?,
                 var taquilla:Double,
                 var cartelera: Boolean,
                 var altitud: Double,
                 var latitud: Double): Serializable {

    override fun toString(): String {
        return " nombre: $nombre, director: $director, taquilla: $taquilla, cartelera: '$cartelera', altitud: $altitud, latitud: $latitud)"
    }
}