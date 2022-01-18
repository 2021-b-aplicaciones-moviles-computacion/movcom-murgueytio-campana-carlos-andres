package com.example.myapplication

import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import android.os.Parcel as Parcel

class EDirector(var id_director:Int, var nombre: String?, var nacionalidad: String?, var isAlive:Int?, var fecha_nac: Date?):Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        Date(parcel.readString())

    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id_director)
        parcel.writeString(nombre)
        parcel.writeString(nacionalidad)
        parcel.writeInt(isAlive!!)
        parcel.writeString(SimpleDateFormat("dd/MM/yyyy").format(fecha_nac))

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Id_Director:$id_director  Nombre:$nombre  Nacionalidad:$nacionalidad  ¿Esta vivo?:$isAlive  FechaNacimiento:${SimpleDateFormat("dd/MM/yyyy").format(fecha_nac)}"
    }

    companion object CREATOR : Parcelable.Creator<EDirector> {
        override fun createFromParcel(parcel: Parcel): EDirector {
            return EDirector(parcel)
        }

        override fun newArray(size: Int): Array<EDirector?> {
            return arrayOfNulls(size)
        }
    }



}