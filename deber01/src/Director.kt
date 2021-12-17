import java.util.*

class Director (
    private var idDirector: Int? = null,
    private var nombreDirector: String? = null,
    private var fechaNacimiento: Date? = null,
    private var nacionalidad: String? = null,
    private var isAlive: Boolean? = null,
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

    fun getNombreDirector(): String?{
        return nombreDirector
    }

    fun setNombreDirector(nuevoNombre:String){
        this.nombreDirector = nuevoNombre
    }

    fun getFechaNacimiento(): Date?{
        return fechaNacimiento
    }

    fun setFechaNacimiento(nuevaFecha : Date){
        this.fechaNacimiento = nuevaFecha
    }

    fun getNacionalidad(): String?{
        return nacionalidad
    }

    fun setNacionalidad(nuevaNacionalidad: String){
        this.nacionalidad = nuevaNacionalidad
    }

    fun getIsAlive(): Boolean?{
        return isAlive
    }

    fun setIsAlive(newAlive : Boolean){
        this.isAlive = newAlive
    }

}