package com.example.aplicacion01

import android.os.Parcel
import android.os.Parcelable

class BEntrenador (
    val nombre:String?,
    val descripcion: String?
    ): Parcelable
{
    override fun toString(): String {
        return "${nombre} ${descripcion}"
    }

    constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString()
    ){

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, flags: Int) {
        p0?.writeString(nombre)
        p0?.writeString(descripcion)
    }

}
