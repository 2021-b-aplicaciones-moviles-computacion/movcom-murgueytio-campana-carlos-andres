package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

class EPelicula(
    var id_pelicula:Int, var id_Director:Int, var nombre:String?, var valoracion:Double,//valoracion
    var presupuesto:Double, //presupuesto
    var cartelera: Boolean
):Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun toString(): String {
        return "Id_Pelicula:$id_pelicula  Id_Director:$id_Director  Nombre:$nombre  Valoracion:$valoracion Presupuesto:$presupuesto Cartelera:$cartelera"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id_pelicula)
        parcel.writeInt(id_Director)
        parcel.writeString(nombre)
        parcel.writeDouble(valoracion)
        parcel.writeDouble(presupuesto)
        parcel.writeByte(if (cartelera) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EPelicula> {
        override fun createFromParcel(parcel: Parcel): EPelicula {
            return EPelicula(parcel)
        }

        override fun newArray(size: Int): Array<EPelicula?> {
            return arrayOfNulls(size)
        }
    }
}