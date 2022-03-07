package com.example.eamenprueba

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Director (var nombre: String?, var nacionalidad: String?, var isAlive:Int?, var fecha_nacimiento: Date?):
    Serializable {
    override fun toString(): String {
        return "  Nombre:$nombre  nacionalidad:$nacionalidad  ¿Está vivo?:$isAlive  Fecha:${SimpleDateFormat("dd/MM/yyyy").format(fecha_nacimiento)}"
    }
}