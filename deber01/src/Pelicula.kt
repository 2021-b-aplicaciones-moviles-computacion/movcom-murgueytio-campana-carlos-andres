import java.util.*

class Pelicula(
    private var idPelicula: Int? = null,
    private var idDirector: Int? = null,
    private var nombrePelicula: String? = null,
    private var valoracionPelicula: Double? = null,
    private var presupuesto: Double? = null,
    private var enCartelera: Boolean? = null,
    )
{
    init{

    }

    fun getidDirector():Int?{
        return idDirector
    }

    fun setidDirector(nuevaId: Int){
        this.idDirector = nuevaId
    }

    fun getidPelicula():Int?{
        return idPelicula
    }

    fun setidPelicula(nuevaIdPel: Int){
        this.idPelicula = nuevaIdPel
    }

    fun getNombrePelicula(): String?{
        return nombrePelicula
    }

    fun setNombrePelicula(nuevoNombre:String){
        this.nombrePelicula = nuevoNombre
    }

    fun getPresupuesto(): Double?{
        return presupuesto
    }

    fun setPresupuesto(nuevoPresupuesto : Double){
        this.presupuesto = nuevoPresupuesto
    }

    fun getValoracionPelicula(): Double?{
        return valoracionPelicula
    }

    fun setvaloracionPelicula(nuevaValoracion: Double){
        this.valoracionPelicula = nuevaValoracion
    }

    fun getEnCartelera(): Boolean?{
        return enCartelera
    }

    fun setEnCartelera(nuevaCartelera : Boolean){
        this.enCartelera = nuevaCartelera
    }

}